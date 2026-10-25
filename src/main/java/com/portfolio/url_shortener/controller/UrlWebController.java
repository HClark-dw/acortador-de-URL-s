package com.portfolio.url_shortener.controller;

import com.portfolio.url_shortener.exception.InvalidUrlException;
import com.portfolio.url_shortener.exception.UrlNotFoundException;
import com.portfolio.url_shortener.model.ShortenedUrl;
import com.portfolio.url_shortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrlWebController {

    private final UrlShortenerService service;

    public UrlWebController(UrlShortenerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("urls", service.getAllUrls());
        model.addAttribute("totalUrls", service.getTotalUrls());
        model.addAttribute("totalClicks", service.getTotalClicks());

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/s/";
        model.addAttribute("baseUrl", baseUrl);

        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("originalUrl") String originalUrl,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        try {
            ShortenedUrl shortened = service.shortenUrl(originalUrl);
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/s/";
            redirectAttributes.addFlashAttribute("successMessage", "¡URL acortada exitosamente!");
            redirectAttributes.addFlashAttribute("generatedShortUrl", baseUrl + shortened.getShortCode());
            redirectAttributes.addFlashAttribute("shortCode", shortened.getShortCode());
            redirectAttributes.addFlashAttribute("originalUrl", shortened.getOriginalUrl());
        } catch (InvalidUrlException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ocurrió un error al acortar la URL: " + e.getMessage());
        }
        return "redirect:/";
    }

    /**
     * Endpoint de Redirección HTTP 302 (Found)
     * Busca la URL original, suma el clic a las estadísticas y redirige al visitante.
     */
    @GetMapping("/s/{code}")
    public RedirectView redirect(@PathVariable("code") String code) {
        String originalUrl = service.getOriginalUrlAndIncrementClicks(code);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        // Código HTTP 302 (Found / Redirección temporal) para evitar que el navegador
        // cachee la redirección permanente (301) y así poder auditar cada clic en las estadísticas.
        redirectView.setStatusCode(org.springframework.http.HttpStatus.FOUND);
        return redirectView;
    }

    @PostMapping("/delete/{id}")
    public String deleteUrl(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteUrl(id);
            redirectAttributes.addFlashAttribute("successMessage", "Enlace eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el enlace.");
        }
        return "redirect:/";
    }
}

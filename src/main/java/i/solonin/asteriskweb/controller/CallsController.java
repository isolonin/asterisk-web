package i.solonin.asteriskweb.controller;

import i.solonin.asteriskweb.model.filter.CallsFilter;
import i.solonin.asteriskweb.service.CallsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import static i.solonin.asteriskweb.controller.CallsController.FILTER_NAME;

@Slf4j
@Controller
@RequiredArgsConstructor
@SessionAttributes(FILTER_NAME)
@RequestMapping("/")
public class CallsController {
    static final String FILTER_NAME = "CALLS_FILTER";
    private final CallsService callsService;

    @ModelAttribute(FILTER_NAME)
    public CallsFilter filter() {
        return new CallsFilter();
    }

    @RequestMapping(params = "reset")
    public String reset(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }

    @RequestMapping
    public String list(@ModelAttribute(FILTER_NAME) CallsFilter filter,
                       @RequestParam(value = "o", defaultValue = "start") String order,
                       @RequestParam(value = "a", defaultValue = "ASC") Sort.Direction asc,
                       @RequestParam(value = "p", defaultValue = "1") int page,
                       Model model) {
        model.addAttribute("result", callsService.findAll(filter, order, asc.equals(Sort.Direction.DESC), page - 1));
        model.addAttribute("o", order);
        model.addAttribute("asc", asc);
        model.addAttribute("page", page);
        return "calls";
    }

    @RequestMapping("/file/{date}/{path}")
    public ResponseEntity<byte[]> wav(@PathVariable("date") String date, @PathVariable("path") String path) {
        byte[] bytes = callsService.getRecord(date + "/" + path);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + path + ".wav\"")
                .contentType(MediaType.asMediaType(MediaType.parseMediaType("audio/wave")))
                .contentLength(bytes.length)
                .body(bytes);
    }
}

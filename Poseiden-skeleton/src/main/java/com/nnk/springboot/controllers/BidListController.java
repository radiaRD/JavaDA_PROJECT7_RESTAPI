package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class BidListController {
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
     bidListService.home(model);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
         bidListService.validate(bid, model);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{bidListId}")
    public String showUpdateForm(@PathVariable("bidListId") Integer bidListId, Model model) {
       bidListService.showUpdateForm( bidListId,model);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{bidListId}")
    public String updateBid(@PathVariable("bidListId") Integer bidListId, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidListService.updateBid(bidListId, bidList, model);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{bidListId}")
    public String deleteBid(@PathVariable("bidListId") Integer bidListId, Model model) {
    bidListService.deleteBid(bidListId,model);
        return "redirect:/bidList/list";
    }
}

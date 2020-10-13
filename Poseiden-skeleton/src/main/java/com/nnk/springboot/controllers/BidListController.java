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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BidListController includes CRUD methods for bids.
 */
@Controller
public class BidListController {
    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    BidListService bidListService;

    /**
     * find all bids.
     *
     * @return the list of bids.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.info("Get bid List");
        bidListService.home(model);
        return "bidList/list";
    }

    /**
     * Add new bid.
     *
     * @return bidList/add URL.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("Get bidList/add URL");
        return "bidList/add";
    }

    /**
     * Validate addition of bid.
     *
     * @return bidList/list URL if bid is validate.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Validate bid add and redirect URL bidList/list");
            bidListService.validate(bid, model);
            return "redirect:/bidList/list";
        }
        logger.info("Bid add not validated, return URL bidList/add");
        return "bidList/add";
    }


    /**
     * Update the bid.
     *
     * @param bidListId id of the bid.
     * @return bidList/update URL.
     */
    @GetMapping("/bidList/update/{bidListId}")
    public String showUpdateForm(@PathVariable("bidListId") Integer bidListId, Model model) {
        logger.info("Get a bid with id :" + bidListId);
        bidListService.showUpdateForm(bidListId, model);
        return "bidList/update";
    }

    /**
     * Validate update bid.
     *
     * @param bidListId id of the bid.
     * @return bidList/list URL if bid is validate.
     */
    @PostMapping("/bidList/update/{bidListId}")
    public String updateBid(@PathVariable("bidListId") Integer bidListId, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Updated not validated, return to URL bidList/update");
            return "bidList/update";
        }
        logger.info("Validate bid update with id : " + bidListId + " and redirect URL bidList/list ");
        bidListService.updateBid(bidListId, bidList, model);
        return "redirect:/bidList/list";
    }

    /**
     * Delete the bid.
     *
     * @param bidListId id of bid.
     * @return bidList/list URL.
     */
    @GetMapping("/bidList/delete/{bidListId}")
    public String deleteBid(@PathVariable("bidListId") Integer bidListId, Model model) {
        logger.info("Delete bid with id : " + bidListId + " and redirect URL bidList/list");
        bidListService.deleteBid(bidListId, model);
        return "redirect:/bidList/list";
    }

}

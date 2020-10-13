package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * BidListService includes CRUD methods for bid.
 */
@Service
public class BidListService {
    private static final Logger logger = LogManager.getLogger(BidListService.class);

    @Autowired
    BidListRepository bidListRepository;

    /**
     * find all bid with bidListRepository.findAll.
     *
     * @return the list of bid.
     */
    public String home(Model model) {
        logger.info("Get bid List");
        model.addAttribute("bidList", bidListRepository.findAll());
        return "bidList/list";
    }

    /**
     * create a new bid with bidListRepository.save.
     */
    public void validate(BidList bidList, Model model) {
        logger.info("Save a new bid");
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());

    }

    /**
     * find the bid by id with bidListRepository.findById.
     */
    public void showUpdateForm(Integer bidListId, Model model) {
        logger.info("Find a bid with id :" + bidListId);
        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + bidListId));
        model.addAttribute("bidList", bidList);
    }

    /**
     * find and update the bid by id .
     */
    public void updateBid(Integer bidListId, BidList bidList, Model model) {
        logger.info("Update the bid with id :" + bidListId);
        bidList.setBidListId(bidListId);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
    }

    /**
     * find and delete the bid by id .
     */
    public void deleteBid(Integer bidListId, Model model) {
        logger.info("delete the bid with id :" + bidListId);
        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + bidListId));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());

    }

}

package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public String home(Model model) {
        model.addAttribute("bidList", bidListRepository.findAll());
        return "bidList/list";
    }

    public void validate(BidList bidList, Model model) {
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());

    }

    public void showUpdateForm(Integer bidListId, Model model) {
        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + bidListId));

        bidList.setBidQuantity(0.0);
        model.addAttribute("bidList", bidList);
    }

    public void updateBid(Integer bidListId, BidList bidList, Model model) {
        bidList.setBidListId(bidListId);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
    }

    public void deleteBid(Integer bidListId, Model model) {
        BidList bidList = bidListRepository.findById(bidListId).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + bidListId));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());

    }

}

package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * TradeService includes CRUD methods for trade.
 */
@Service
public class TradeService {
    private static final Logger logger = LogManager.getLogger(TradeService.class);

    @Autowired
    TradeRepository tradeRepository;

    /**
     * find all trade with tradeRepository.findAll.
     *
     * @return the list of trade.
     */
    public String home(Model model) {
        logger.info("Get trade List");
        model.addAttribute("trade", tradeRepository.findAll());
        return "trade/list";
    }

    /**
     * create a new trade with tradeRepository.save.
     */
    public void validate(Trade trade, Model model) {
        logger.info("Save a new trade");
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());
    }

    /**
     * find the trade by id with tradeRepository.findById.
     */
    public void showUpdateForm(Integer tradeId, Model model) {
        logger.info("Find a trade with id :" + tradeId);
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + tradeId));
        model.addAttribute("trade", trade);

    }

    /**
     * find and update the trade by id .
     */
    public void updateTrade(Integer tradeId, Trade trade,
                            Model model) {
        logger.info("Update the trade with id :" + tradeId);
        trade.setTradeId(tradeId);
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());

    }

    /**
     * find and delete the trade by id .
     */
    public void deleteTrade(Integer tradeId, Model model) {
        logger.info("Delete the trade with id :" + tradeId);
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + tradeId));
        tradeRepository.delete(trade);
        model.addAttribute("trade", tradeRepository.findAll());
    }

}

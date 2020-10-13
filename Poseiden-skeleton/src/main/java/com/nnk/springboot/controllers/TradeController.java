package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * TradeController includes CRUD methods for trade.
 */
@Controller
public class TradeController {
    private static final Logger logger = LogManager.getLogger(TradeController.class);

    @Autowired
    TradeService tradeService;

    /**
     * find all trade.
     *
     * @return the list of trade.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        logger.info("Get trade List");
        tradeService.home(model);
        return "trade/list";
    }

    /**
     * Add new trade.
     *
     * @return trade/add URL.
     */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        logger.info("Get trade/add URL");
        return "trade/add";
    }

    /**
     * Validate addition of trade.
     *
     * @return trade/list URL if trade is validate.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Validate trade add and redirect URL trade/list");
            tradeService.validate(trade, model);
            return "redirect:/trade/list";
        }
        logger.info("trade add not validated, return URL trade/add");
        return "trade/add";
    }

    /**
     * Update the trade.
     *
     * @param tradeId id of the trade.
     * @return trade/update URL.
     */
    @GetMapping("/trade/update/{tradeId}")
    public String showUpdateForm(@PathVariable("tradeId") Integer tradeId, Model model) {
        logger.info("Get a trade with id :" + tradeId);
        tradeService.showUpdateForm(tradeId, model);
        return "trade/update";
    }

    /**
     * Validate update trade.
     *
     * @param tradeId id of the trade.
     * @return trade/list URL if trade is validate.
     */
    @PostMapping("/trade/update/{tradeId}")
    public String updateTrade(@PathVariable("tradeId") Integer tradeId, @Valid Trade trade,
                              BindingResult result, Model model) {
        logger.info("Updated not validated, return to URL trade/update");
        if (result.hasErrors()) {
            return "trade/update";
        }
        logger.info("Validate trade update with id : " + tradeId + " and redirect URL trade/list ");
        tradeService.updateTrade(tradeId, trade, model);
        return "redirect:/trade/list";
    }

    /**
     * Delete the trade.
     *
     * @param tradeId id of trade.
     * @return trade/list URL.
     */
    @GetMapping("/trade/delete/{tradeId}")
    public String deleteTrade(@PathVariable("tradeId") Integer tradeId, Model model) {
        logger.info("Delete the trade with id : " + tradeId + " and redirect URL trade/list");
        tradeService.deleteTrade(tradeId, model);
        return "redirect:/trade/list";
    }
}

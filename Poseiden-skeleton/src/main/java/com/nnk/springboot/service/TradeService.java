package com.nnk.springboot.service;

        import com.nnk.springboot.domain.Trade;
        import com.nnk.springboot.domain.User;
        import com.nnk.springboot.repositories.TradeRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.ui.Model;


@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;


    public String home(Model model) {
        model.addAttribute("trade", tradeRepository.findAll());
        return "trade/list";
    }

    public void validate(Trade trade, Model model) {
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());
    }

    public void showUpdateForm(Integer tradeId, Model model) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + tradeId));
        trade.setBuyQuantity(0.0);
        trade.setBook("");
        trade.setSellPrice(0.0);
        model.addAttribute("trade", trade);

    }

    public void updateTrade(Integer tradeId, Trade trade,
                            Model model) {
        trade.setTradeId(tradeId);
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());

    }

    public void deleteTrade(Integer tradeId, Model model) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + tradeId));
        tradeRepository.delete(trade);
        model.addAttribute("trade", tradeRepository.findAll());
    }

}

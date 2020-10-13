package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.CurvePointService;
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
 * CurveController includes CRUD methods for curves.
 */
@Controller
public class CurveController {
    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @Autowired
    CurvePointService curvePointService;

    /**
     * find all curve.
     *
     * @return the list of curve points.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        logger.info("Get curve points List");
        curvePointService.home(model);
        return "curvePoint/list";
    }

    /**
     * Add new curve point.
     *
     * @return curvePoint/add URL.
     */
    @GetMapping("/curvePoint/add")
    public String addForm(CurvePoint bid) {
        logger.info("Get curvePoint/add URL");
        return "curvePoint/add";
    }

    /**
     * Validate addition of curve point.
     *
     * @return curvePoint/list URL if curve point is validate.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Validate curve point add and redirect URL curvePoint/list");
            curvePointService.validate(curvePoint, model);
            return "redirect:/curvePoint/list";
        }
        logger.info("Bid add not validated, return URL curvePoint/add");
        return "curvePoint/add";
    }

    /**
     * Update the curvePoint.
     *
     * @param id id of the curve point.
     * @return curvePoint/update URL.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Get a curve point with id :" + id);
        curvePointService.showUpdateForm(id, model);
        return "curvePoint/update";
    }

    /**
     * Validate update curve point.
     *
     * @param id id of the curve point.
     * @return curvePoint/list URL if curve point is validate.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("Updated not validated, return to URL curvePoint/update");
            return "curvePoint/update";
        }
        logger.info("Validate curve point update with id : " + id + " and redirect URL curvePoint/list ");
        curvePointService.updateCurve(id, curvePoint, model);
        return "redirect:/curvePoint/list";
    }


    /**
     * Delete the curve point.
     *
     * @param id id of curve point.
     * @return curvePoint/list URL.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        logger.info("Delete the curve point with id : " + id + " and redirect URL curvePoint/list");
        curvePointService.deleteCurve(id, model);
        return "redirect:/curvePoint/list";
    }
}

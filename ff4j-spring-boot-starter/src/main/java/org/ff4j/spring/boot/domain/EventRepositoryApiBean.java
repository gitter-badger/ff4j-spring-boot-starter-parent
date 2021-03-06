package org.ff4j.spring.boot.domain;


import lombok.Getter;
import org.ff4j.audit.graph.BarChart;
import org.ff4j.audit.graph.PieChart;
import org.ff4j.audit.graph.PieSector;
import org.ff4j.audit.repository.EventRepository;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public class EventRepositoryApiBean implements Serializable {

    private static final long serialVersionUID = -3365322115944400241L;

    @Getter
    private String type;

    @Getter
    private int hitCount;

    @Getter
    private PieChartApiBean eventsPie;

    @Getter
    private BarChartApiBean barChart;

    public EventRepositoryApiBean(EventRepository evtRepository, Long start, Long end) {
        type = evtRepository.getClass().getCanonicalName();
        Long computedStart = start;
        Long computedEnd = end;
        // Today
        if (start == null) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            computedStart = c.getTimeInMillis();
        }
        // Tomorrow 00:00
        if (end == null) {
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
            c2.set(Calendar.HOUR_OF_DAY, 0);
            c2.set(Calendar.MINUTE, 0);
            c2.set(Calendar.SECOND, 0);
            computedEnd = c2.getTimeInMillis();
        }
        // Create PIE
        PieChart pie = evtRepository.getHitsPieChart(computedStart, computedEnd);
        eventsPie = new PieChartApiBean(pie);
        // Create BARCHART
        BarChart bc = evtRepository.getHitsBarChart(computedStart, computedEnd, 24);
        barChart = new BarChartApiBean(bc);
        // Total Count
        for (PieSector sector : pie.getSectors()) {
            hitCount += sector.getValue();
        }
    }
}

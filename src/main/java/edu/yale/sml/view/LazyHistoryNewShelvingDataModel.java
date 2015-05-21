package edu.yale.sml.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.yale.sml.persistence.ShelvingDAO;
import edu.yale.sml.persistence.ShelvingHibernateDAO;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import edu.yale.sml.logic.ShelvingComparator;
import edu.yale.sml.model.Shelving;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class LazyHistoryNewShelvingDataModel extends LazyDataModel<Shelving> {

    private final Logger logger = getLogger(this.getClass());

    private List<Shelving> datasource;

    private int dataSourceSize = 0;

    private final ShelvingDAO dao = new ShelvingHibernateDAO();

    @Override
    public List<Shelving> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                               Map<String, String> filters) {

        int resultCount = dataSourceSize;
        List<Shelving> data = new ArrayList<Shelving>();
        boolean filtered = false;
        int filteredCount = 0;

        try {
            if (sortField != null && sortOrder == SortOrder.ASCENDING) {
                datasource = dao.findPagedResult(Shelving.class, first, first + pageSize, "c." + sortField + " asc");

            } else if (sortField != null && sortOrder == SortOrder.DESCENDING) {
                datasource = dao.findPagedResult(Shelving.class, first, first + pageSize, "c." + sortField + " desc");
            } else {
                if (filters.get("NETID") != null && filters.size() == 1) {
                    datasource = dao.findPagedResultByType(Shelving.class, first, first + pageSize,
                            "c.creationDate desc", filters.get("NETID").toString(), "NETID");
                    filteredCount = dao.findByLevelCount(Shelving.class, filters.get("NETID").toString(), "NETID");
                    filtered = true;
                } else if (filters.get("SCANLOCATION") != null && filters.size() == 1) {
                    datasource = dao.findPagedResultByType(Shelving.class, first, first + pageSize,
                            "c.creationDate desc", filters.get("SCANLOCATION").toString(), "SCANLOCATION");

                    filteredCount = dao.findByLevelCount(Shelving.class,
                            filters.get("SCANLOCATION").toString(), "SCANLOCATION");
                    filtered = true;
                } else if (filters.get("SCANLOCATION") != null && filters.get("NETID") != null && filters.size() == 2) {
                    datasource = dao.findPagedResultByType(Shelving.class, first, first + pageSize,
                            "c.creationDate desc", filters.get("SCANLOCATION").toString(),
                            "SCANLOCATION", filters.get("NETID"), "NETID");
                    filteredCount = dao.findByLevelCount(Shelving.class, filters.get("NETID").toString(), "NETID",
                            filters.get("SCANLOCATION").toString(), "SCANLOCATION");
                    filtered = true;
                } else {
                    datasource = dao.findPagedResult(Shelving.class, first, first + pageSize, "c.creationDate desc");
                }
            }
        } catch (Throwable t) {
            logger.error("Error", t);
        }

        if (sortField != null) {
            Collections.sort(data, new ShelvingComparator(sortField, sortOrder));
        }

        for (final Shelving history : datasource) {
            boolean match = true;
            for (final Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                try {
                    String filterProperty = it.next();
                    String filterValue = filters.get(filterProperty);
                    Object h = history.getClass().getDeclaredField(filterProperty.trim()).get(history);
                    String fieldValue = String.valueOf(history.getClass().getDeclaredField(filterProperty.trim()).get(history));
                    if (filterValue == null || fieldValue.startsWith(filterValue)) {
                        match = true;
                    } else {
                        match = false;
                        break;
                    }
                } catch (NoSuchFieldException e) {
                    match = false;
                } catch (NullPointerException n) {
                    match = false;
                } catch (IllegalAccessException i1) {
                    match = false;
                } catch (IllegalArgumentException i2) {
                    match = false;
                } catch (java.lang.ExceptionInInitializerError itr) {
                    match = false;
                } catch (Throwable t) {
                    match = false;
                }
            }
            if (match) {
                data.add(history);
            }
        }

        //int dataSize = resultCount;

        if (filtered) {
            this.setRowCount(filteredCount);
        } else {
            this.setRowCount(resultCount);
        }

        return data;
    }

    public LazyHistoryNewShelvingDataModel(List<Shelving> datasource) {
        this.datasource = datasource;
    }

    public LazyHistoryNewShelvingDataModel(int historyAsListSize) {
        this.dataSourceSize = historyAsListSize;
    }
}

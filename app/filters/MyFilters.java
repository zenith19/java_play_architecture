package filters;

import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

/**
 * Created by rownak on 11/10/16.
 */
public class MyFilters  extends DefaultHttpFilters {
    @Inject
    public MyFilters(CORSFilter corsFilter) {
        super(corsFilter);
    }
}

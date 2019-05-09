package model;

/**
 * TODO there are more measures we need to add
 */
public enum MeasureType {

    SCHWARTZBERG_COMPACTNESS {
        /**
         * The ratio between the perimeter and the circumference of a circle with equivalent area
         */
        @Override
        public double calculateMeasure(District d) {
            final double radius = Math.sqrt(d.getArea()/Math.PI);
            return ((2.0*radius*Math.PI) / d.getPerimeter());
        }
    },
    REOCK_COMPACTNESS {
        /**
         * The ratio between the district area and the area of a bounding circle
         */
        @Override
        public double calculateMeasure(District d) {
            return d.getArea()/d.getBoundingCircleArea();
        }
    },
    POLSBY_POPPER_COMPACTNESS {
        /**
         *the ratio of the area of the district to the area of a circle whose circumference is equal to the perimeter of the district
         */
        @Override
        public double calculateMeasure(District d) {
            return 4 * Math.PI * (d.getPerimeter()/(Math.pow(d.getArea(), 2)));
        }
    },
    CONVEX_HULL_COMPACTNESS {
        /**
         * The ratio between the district area and the area of a convex hull
         */
        @Override
        public double calculateMeasure(District d) {
            return d.getArea()/d.getConvexHull();
        }
    },
    EFFICIENCY_GAP {
        /**
         * the difference in the wasted votes of the two parties divided by the total population
         */
        @Override
        public double calculateMeasure(District d) {
            return 1.0 - (Math.abs(d.wastedDemVotes() - d.wastedRepVotes()) / (double)d.getPopulation());
        }
    },
    POPULATION_EQUALITY {
        /**
         * Racial equality by population
         */
        @Override
        public double calculateMeasure(District d) {
            return Math.abs((d.getPopulation() / DemographicType.values().length) - d.getDemographics().getDemographicPopulation().get(DemographicType.WHITE));
        }
    };

    /**
     * Gives the normalized [0-1] value of a measure for a particular District. In our Context,
     * having a higher calculateMeasure() output is better.
     */
    public abstract double calculateMeasure(District d);
}

package model;

public enum MeasureType {

    SCHWARTZBERG_COMPACTNESS {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    },
    REOCK_COMPACTNESS {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    },
    POLSBY_POPPER_COMPACTNESS {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    },
    CONVEX_HULL_COMPACTNESS {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    },
    EFFICIENCY_GAP {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    },
    PARTISAIN_FAIRNESS {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    },
    RACIAL_FAIRNESS {
        @Override
        public double calculateMeasure(District D) {
            return -1.0;//TODO
        }
    };

    public abstract double calculateMeasure(District D);
}

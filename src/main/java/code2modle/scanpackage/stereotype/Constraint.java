package code2modle.scanpackage.stereotype;

public enum Constraint {
    // AggregatePart constraint
    CONSTRAINT_01(201, "Only Entities and Value Objects may be Aggregate parts"),
    CONSTRAINT_02(202, "Assigned Aggregate root must have Aggregate Root stereotype"),
    CONSTRAINT_03(203, "No incoming Associations from outside the Aggregate"),
    CONSTRAINT_04(204, "Must be in same Bounded Context as Aggregate root"),

    // AggregateRoot constraint
    CONSTRAINT_05(401, "Only Entities may be Aggregate roots"),
    CONSTRAINT_06(402, "Aggregate must contain at least one part"),

    // Entity
    CONSTRAINT_07(801, "One Operation or at least one Property defines the identity"),

    //Repository
    CONSTRAINT_08(1601, "Class has no other stereotypes"),
    CONSTRAINT_09(1602, "Class contains only Operations and at least one"),
    CONSTRAINT_10(1603, "Outgoing Associations must point to Entities or Value Objects"),

    //Service
    CONSTRAINT_11(3201, "Class has no other stereotypes"),
    CONSTRAINT_12(3202, "Class contains only Operations and at least one"),

    //Specification
    CONSTRAINT_13(6401, "Class has no other stereotypes"),
    CONSTRAINT_14(6402, "Class contains at least one validation Operation"),
    CONSTRAINT_15(6403, "At least one domain object is specified"),
    CONSTRAINT_16(6404, "Validation Operation has Parameter typed as specified ob ject"),

    //Closure
    CONSTRAINT_17(12801,"Must not be specification validation or identity Operation"),
    CONSTRAINT_18(12802,"Return Parameter type must conform input Parameter type"),

    //DefinesIdentity
    CONSTRAINT_19(25601,"Must not be specification validation Operation"),
    CONSTRAINT_20(25602,"May only be applied within Entities"),

    //SideEffectFree
    CONSTRAINT_21(51201,"Operation must have a return Parameter"),

    //ValidateSpec
    CONSTRAINT_22(102401,"Must have Boolean-typed return Parameter"),
    CONSTRAINT_23(102402,"May only be applied within Specifications"),

    //BoundedContext
    CONSTRAINT_24(204801,"Must not have Module stereotype"),
    CONSTRAINT_25(204802,"Must not be nested, i.e. part of another Package");


    private  Integer code;
    private  String rule;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    Constraint(Integer code, String rule) {
        this.code = code;
        this.rule = rule;
    }


}


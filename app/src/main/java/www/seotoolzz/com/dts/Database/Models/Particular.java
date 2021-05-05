package www.seotoolzz.com.dts.Database.Models;

public class Particular {
    public String id;

    public String item_id;

    public String name;

    public String unit;

    public String description;

    public String estimated_cost;

    public String cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimated_cost() {
        return estimated_cost;
    }

    public void setEstimated_cost(String estimated_cost) {
        this.estimated_cost = estimated_cost;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}

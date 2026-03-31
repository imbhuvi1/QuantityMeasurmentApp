package com.apps.quantitymeasurement.entity;

import com.apps.quantitymeasurement.model.QuantityModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_entity", indexes = {
        @Index(name = "idx_operation", columnList = "operation"),
        @Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
        @Index(name = "idx_created_at", columnList = "created_at")
        })
@Data   // -> Automatically Creates Getter , Setter , toString() , equals() , hashCode() and we can also use @Getter and @Setter Annotation for each field
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_value", nullable = false)
    private double thisValue;
    @Column(name = "this_unit", nullable = false)
    private String thisUnit;
    @Column(name = "this_measurement_type", nullable = false)
    private String thisMeasurementType;

    @Column(name = "that_value")
    private Double thatValue;
    @Column(name = "that_unit")
    private String thatUnit;
    @Column(name = "that_measurement_type")
    private String thatMeasurementType;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "result_value")
    private Double resultValue;
    @Column(name = "result_unit")
    private String resultUnit;
    @Column(name = "result_measurement_type")
    private String resultMeasurementType;

    @Column(name = "result_string")
    private String resultString;

    @Column(name = "is_error")
    private boolean isError;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    // Base Constructor
    public QuantityMeasurementEntity(QuantityModel<?> thisQuantity , QuantityModel<?> thatQuantity , String operation){
        if(thisQuantity != null){
            this.thisValue = thisQuantity.getValue();
            this.thisUnit = thisQuantity.getUnit().getUnitName();
            this.thisMeasurementType = thisQuantity.getUnit().getMeasurementType();
        }
        if(thatQuantity != null){
            this.thatValue = thatQuantity.getValue();
            this.thatUnit = thatQuantity.getUnit().getUnitName();
            this.thatMeasurementType = thatQuantity.getUnit().getMeasurementType();
        }
        this.operation = operation;
    }

    // Constructor for Comparison Operation
    public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation, String resultString) {
        this(thisQuantity,thatQuantity,operation);
        this.resultString = resultString;
        this.isError = false;
    }

    // Constructor for conversion
    public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, String operation, QuantityModel<?> resultQuantity) {
        this(thisQuantity, null, operation);
        this.resultValue = resultQuantity.getValue();
        this.resultUnit = resultQuantity.getUnit().getUnitName();
        this.resultMeasurementType = resultQuantity.getUnit().getMeasurementType();
        this.isError = false;
    }

    // Constructor for Arithmetic operations
    public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation, QuantityModel<?> resultQuantity) {
        this(thisQuantity, thatQuantity, operation);
        this.resultValue = resultQuantity.getValue();
        this.resultUnit = resultQuantity.getUnit().getUnitName();
        this.resultMeasurementType = resultQuantity.getUnit().getMeasurementType();
        this.isError = false;
    }

    // Constructor for Errors
    public QuantityMeasurementEntity(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity, String operation, String errorMessage, boolean isError) {
        this(thisQuantity, thatQuantity, operation);
        this.errorMessage = errorMessage;
        this.isError = isError;
    }

    // This method runs before you want to create data
    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // This method runs when you want to update the data
    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
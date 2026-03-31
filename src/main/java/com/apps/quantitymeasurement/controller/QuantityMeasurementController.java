package com.apps.quantitymeasurement.controller;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.dto.TwoQuantityRequest;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quantity")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    // 1. Compare
    @PostMapping("/compare")
    public boolean compare(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        return service.compare(request.getQ1(), request.getQ2());
    }

    // 2. Convert
    @PostMapping("/convert")
    public QuantityDTO convert(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        return service.convert(request.getQ1(), request.getQ2());
    }

    // 3. Add
    @PostMapping("/add")
    public QuantityDTO add(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        if(request.getTargetUnit() == null) {
            return service.add(request.getQ1(), request.getQ2());
        }
        return service.add(request.getQ1(), request.getQ2(), request.getTargetUnit());
    }

    // 4. Subtract
    @PostMapping("/subtract")
    public QuantityDTO subtract(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        if(request.getTargetUnit() == null) {
            return service.subtract(request.getQ1(), request.getQ2());
        }
        return service.subtract(request.getQ1(), request.getQ2(), request.getTargetUnit());
    }

    // 5. Divide
    @PostMapping("/divide")
    public double divide(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        return service.divide(request.getQ1(), request.getQ2());
    }

    // 5. Get History
    @GetMapping("/getHistory")
    public List<QuantityMeasurementEntity> getHistory() {
        return service.getHistory();
    }

    // 6. Find By Operation
    @GetMapping("/operation")
    public List<QuantityMeasurementEntity> findByOperation(@RequestParam String operation){
        return service.findByOperation(operation);
    }

    // 7. Find By This Measurement Type
    @GetMapping("/measurementType")
    public List<QuantityMeasurementEntity> findByThisMeasurementType(@RequestParam String measurementType){
        return service.findByThisMeasurementType(measurementType);
    }

    // 8. Find By Created At After
//    @GetMapping("/findByCreatedAtAfter")
//    public List<QuantityMeasurementEntity> findByCreatedAtAfter(@RequestParam )

    // 9. Find By Operation and isError false
    @GetMapping("/findByOperation")
    public List<QuantityMeasurementEntity> findByOperationAndIsErrorFalse(@RequestParam String operation){
        return service.findByOperationAndIsErrorFalse(operation);
    }

    // 10. Count By Operation and isError false
    @GetMapping("/countByOperation")
    public long countByOperation(@RequestParam String operation){
        return service.countByOperationAndIsErrorFalse(operation);
    }

    // 11. Find By is Error true
    @GetMapping("/errorTrue")
    public List<QuantityMeasurementEntity> findByIsErrorTrue(){
        return service.findByIsErrorTrue();
    }

    // 12. Delete by id
    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
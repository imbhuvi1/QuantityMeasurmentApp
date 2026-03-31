package com.apps.quantitymeasurement.controller;
import com.apps.quantitymeasurement.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<Boolean>> compare(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        return ResponseEntity.ok(ApiResponse.ok(service.compare(request.getQ1(), request.getQ2())));
    }

    // 2. Convert
    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<QuantityDTO>> convert(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        return ResponseEntity.ok(ApiResponse.ok(service.convert(request.getQ1(), request.getQ2())));
    }

    // 3. Add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<QuantityDTO>> add(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        QuantityDTO result = request.getTargetUnit() == null
                ? service.add(request.getQ1(), request.getQ2())
                : service.add(request.getQ1(), request.getQ2(), request.getTargetUnit());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    // 4. Subtract
    @PostMapping("/subtract")
    public ResponseEntity<ApiResponse<QuantityDTO>> subtract(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        QuantityDTO result = request.getTargetUnit() == null
                ? service.subtract(request.getQ1(), request.getQ2())
                : service.subtract(request.getQ1(), request.getQ2(), request.getTargetUnit());
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    // 5. Divide
    @PostMapping("/divide")
    public ResponseEntity<ApiResponse<Double>> divide(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        return ResponseEntity.ok(ApiResponse.ok(service.divide(request.getQ1(), request.getQ2())));
    }

    // 6. Get History
    @GetMapping("/getHistory")
    public ResponseEntity<ApiResponse<List<QuantityMeasurementEntity>>> getHistory() {
        return ResponseEntity.ok(ApiResponse.ok(service.getHistory()));
    }

    // 7. Find By Operation
    @GetMapping("/operation")
    public ResponseEntity<ApiResponse<List<QuantityMeasurementEntity>>> findByOperation(@RequestParam String operation) {
        return ResponseEntity.ok(ApiResponse.ok(service.findByOperation(operation)));
    }

    // 8. Find By Measurement Type
    @GetMapping("/measurementType")
    public ResponseEntity<ApiResponse<List<QuantityMeasurementEntity>>> findByThisMeasurementType(@RequestParam String measurementType) {
        return ResponseEntity.ok(ApiResponse.ok(service.findByThisMeasurementType(measurementType)));
    }

    // 9. Find By Operation and isError false
    @GetMapping("/findByOperation")
    public ResponseEntity<ApiResponse<List<QuantityMeasurementEntity>>> findByOperationAndIsErrorFalse(@RequestParam String operation) {
        return ResponseEntity.ok(ApiResponse.ok(service.findByOperationAndIsErrorFalse(operation)));
    }

    // 10. Count By Operation and isError false
    @GetMapping("/countByOperation")
    public ResponseEntity<ApiResponse<Long>> countByOperation(@RequestParam String operation) {
        return ResponseEntity.ok(ApiResponse.ok(service.countByOperationAndIsErrorFalse(operation)));
    }

    // 11. Find By isError true
    @GetMapping("/errorTrue")
    public ResponseEntity<ApiResponse<List<QuantityMeasurementEntity>>> findByIsErrorTrue() {
        return ResponseEntity.ok(ApiResponse.ok(service.findByIsErrorTrue()));
    }

    // 12. Delete by id
    @DeleteMapping("/deleteById")
    public ResponseEntity<ApiResponse<String>> deleteById(@RequestParam Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Record deleted successfully"));
    }

    // 13. Delete selected by ids
    @DeleteMapping("/deleteByIds")
    public ResponseEntity<ApiResponse<String>> deleteByIds(@RequestBody List<Long> ids) {
        service.deleteAllByIds(ids);
        return ResponseEntity.ok(ApiResponse.ok("Selected records deleted successfully"));
    }

    // 14. Delete all history
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        service.deleteAllHistory();
        return ResponseEntity.ok(ApiResponse.ok("All history deleted successfully"));
    }

}
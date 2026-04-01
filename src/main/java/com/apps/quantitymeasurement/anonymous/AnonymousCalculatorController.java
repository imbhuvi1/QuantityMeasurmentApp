package com.apps.quantitymeasurement.anonymous;

import com.apps.quantitymeasurement.dto.ApiResponse;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.dto.TwoQuantityRequest;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quantity")
@CrossOrigin(origins = "*")
public class AnonymousCalculatorController {

    @Autowired
    private IQuantityMeasurementService quantityService;

    // Anonymous compare - no authentication required
    @PostMapping("/compare")
    public ResponseEntity<ApiResponse<Boolean>> compare(@RequestBody TwoQuantityRequest request) {
        try {
            boolean result = quantityService.compare(request.getQuantity1(), request.getQuantity2());
            return ResponseEntity.ok(new ApiResponse<>(true, "Comparison completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body(new ApiResponse<>(false, "Comparison failed: " + e.getMessage(), null));
        }
    }

    // Anonymous convert - no authentication required
    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<QuantityDTO>> convert(@RequestBody TwoQuantityRequest request) {
        try {
            QuantityDTO result = quantityService.convert(request.getQuantity1(), request.getQuantity2());
            return ResponseEntity.ok(new ApiResponse<>(true, "Conversion completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body(new ApiResponse<>(false, "Conversion failed: " + e.getMessage(), null));
        }
    }

    // Anonymous add - no authentication required
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<QuantityDTO>> add(@RequestBody TwoQuantityRequest request) {
        try {
            QuantityDTO result = quantityService.add(request.getQuantity1(), request.getQuantity2());
            return ResponseEntity.ok(new ApiResponse<>(true, "Addition completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body(new ApiResponse<>(false, "Addition failed: " + e.getMessage(), null));
        }
    }

    // Anonymous subtract - no authentication required
    @PostMapping("/subtract")
    public ResponseEntity<ApiResponse<QuantityDTO>> subtract(@RequestBody TwoQuantityRequest request) {
        try {
            QuantityDTO result = quantityService.subtract(request.getQuantity1(), request.getQuantity2());
            return ResponseEntity.ok(new ApiResponse<>(true, "Subtraction completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body(new ApiResponse<>(false, "Subtraction failed: " + e.getMessage(), null));
        }
    }

    // Anonymous divide - no authentication required
    @PostMapping("/divide")
    public ResponseEntity<ApiResponse<Double>> divide(@RequestBody TwoQuantityRequest request) {
        try {
            double result = quantityService.divide(request.getQuantity1(), request.getQuantity2());
            return ResponseEntity.ok(new ApiResponse<>(true, "Division completed", result));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body(new ApiResponse<>(false, "Division failed: " + e.getMessage(), null));
        }
    }
}
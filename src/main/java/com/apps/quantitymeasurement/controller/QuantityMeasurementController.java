package com.apps.quantitymeasurement.controller;
import com.apps.quantitymeasurement.dto.ApiResponse;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.dto.TwoQuantityRequest;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.AnonymousCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quantity")
@CrossOrigin(origins = "*")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;
    private final AnonymousCalculatorService anonymousService;

    public QuantityMeasurementController(IQuantityMeasurementService service, AnonymousCalculatorService anonymousService) {
        this.service = service;
        this.anonymousService = anonymousService;
    }

    private boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
    }

    // 1. Compare
    @PostMapping("/compare")
    public ResponseEntity<ApiResponse<Boolean>> compare(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        if (isAuthenticated()) {
            return ResponseEntity.ok(ApiResponse.ok(service.compare(request.getQ1(), request.getQ2())));
        } else {
            return ResponseEntity.ok(ApiResponse.ok(anonymousService.compare(request.getQ1(), request.getQ2())));
        }
    }

    // 2. Convert
    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<QuantityDTO>> convert(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        if (isAuthenticated()) {
            return ResponseEntity.ok(ApiResponse.ok(service.convert(request.getQ1(), request.getQ2())));
        } else {
            return ResponseEntity.ok(ApiResponse.ok(anonymousService.convert(request.getQ1(), request.getQ2())));
        }
    }

    // 3. Add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<QuantityDTO>> add(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        QuantityDTO result;
        if (isAuthenticated()) {
            result = request.getTargetUnit() == null
                    ? service.add(request.getQ1(), request.getQ2())
                    : service.add(request.getQ1(), request.getQ2(), request.getTargetUnit());
        } else {
            result = request.getTargetUnit() == null
                    ? anonymousService.add(request.getQ1(), request.getQ2())
                    : anonymousService.add(request.getQ1(), request.getQ2(), request.getTargetUnit());
        }
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    // 4. Subtract
    @PostMapping("/subtract")
    public ResponseEntity<ApiResponse<QuantityDTO>> subtract(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        QuantityDTO result;
        if (isAuthenticated()) {
            result = request.getTargetUnit() == null
                    ? service.subtract(request.getQ1(), request.getQ2())
                    : service.subtract(request.getQ1(), request.getQ2(), request.getTargetUnit());
        } else {
            result = request.getTargetUnit() == null
                    ? anonymousService.subtract(request.getQ1(), request.getQ2())
                    : anonymousService.subtract(request.getQ1(), request.getQ2(), request.getTargetUnit());
        }
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    // 5. Divide
    @PostMapping("/divide")
    public ResponseEntity<ApiResponse<Double>> divide(@RequestBody TwoQuantityRequest request) throws QuantityMeasurementException {
        if (isAuthenticated()) {
            return ResponseEntity.ok(ApiResponse.ok(service.divide(request.getQ1(), request.getQ2())));
        } else {
            return ResponseEntity.ok(ApiResponse.ok(anonymousService.divide(request.getQ1(), request.getQ2())));
        }
    }

    // 6. Get History
    @GetMapping("/getHistory")
    public ResponseEntity<ApiResponse<List<QuantityMeasurementEntity>>> getHistory() {
        return ResponseEntity.ok(ApiResponse.ok(service.getHistory()));
    }

    // 7. Delete by id
    @DeleteMapping("/deleteById")
    public ResponseEntity<ApiResponse<String>> deleteById(@RequestParam Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Record deleted successfully"));
    }

    // 8. Delete all history
    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        service.deleteAllHistory();
        return ResponseEntity.ok(ApiResponse.ok("All history deleted successfully"));
    }

}
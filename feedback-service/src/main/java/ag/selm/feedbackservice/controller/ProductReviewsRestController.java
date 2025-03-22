package ag.selm.feedbackservice.controller;

import ag.selm.feedbackservice.controller.payload.NewProductReviewPayload;
import ag.selm.feedbackservice.entity.ProductReview;
import ag.selm.feedbackservice.service.ProductReviewsService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback-api/product-reviews")
public class ProductReviewsRestController {

    private final ProductReviewsService productReviewsService;

    @GetMapping("by-product-id/{productId:\\d+}")
    public Flux<ProductReview> findProductReviewsByProductId(@PathVariable int productId) {
        return this.productReviewsService.findProductReviewsByProduct(productId);
    }

    @PostMapping()
    public Mono<ResponseEntity<ProductReview>> createProductReview(
            @Valid @RequestBody Mono<NewProductReviewPayload> payloadMono,
            UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> this.productReviewsService.createProductReview(
                        payload.productId(), payload.rating(), payload.review())
                        .map(productReview -> ResponseEntity
                                .created(uriComponentsBuilder.replacePath("feedback-api/product-reviews/{id}")
                                        .build(productReview.getId()))
                                .body(productReview)));
    }
}

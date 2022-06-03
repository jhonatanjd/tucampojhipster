package co.edu.sena.web.rest;

import co.edu.sena.repository.OrderDetaiRepository;
import co.edu.sena.service.OrderDetaiService;
import co.edu.sena.service.dto.OrderDetaiDTO;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.OrderDetai}.
 */
@RestController
@RequestMapping("/api")
public class OrderDetaiResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetaiResource.class);

    private static final String ENTITY_NAME = "orderDetai";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDetaiService orderDetaiService;

    private final OrderDetaiRepository orderDetaiRepository;

    public OrderDetaiResource(OrderDetaiService orderDetaiService, OrderDetaiRepository orderDetaiRepository) {
        this.orderDetaiService = orderDetaiService;
        this.orderDetaiRepository = orderDetaiRepository;
    }

    /**
     * {@code POST  /order-detais} : Create a new orderDetai.
     *
     * @param orderDetaiDTO the orderDetaiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetaiDTO, or with status {@code 400 (Bad Request)} if the orderDetai has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-detais")
    public ResponseEntity<OrderDetaiDTO> createOrderDetai(@RequestBody OrderDetaiDTO orderDetaiDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetai : {}", orderDetaiDTO);
        if (orderDetaiDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderDetai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDetaiDTO result = orderDetaiService.save(orderDetaiDTO);
        return ResponseEntity
            .created(new URI("/api/order-detais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-detais/:id} : Updates an existing orderDetai.
     *
     * @param id the id of the orderDetaiDTO to save.
     * @param orderDetaiDTO the orderDetaiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetaiDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetaiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetaiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-detais/{id}")
    public ResponseEntity<OrderDetaiDTO> updateOrderDetai(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetaiDTO orderDetaiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderDetai : {}, {}", id, orderDetaiDTO);
        if (orderDetaiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetaiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetaiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderDetaiDTO result = orderDetaiService.update(orderDetaiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDetaiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-detais/:id} : Partial updates given fields of an existing orderDetai, field will ignore if it is null
     *
     * @param id the id of the orderDetaiDTO to save.
     * @param orderDetaiDTO the orderDetaiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetaiDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetaiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDetaiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDetaiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-detais/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDetaiDTO> partialUpdateOrderDetai(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetaiDTO orderDetaiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderDetai partially : {}, {}", id, orderDetaiDTO);
        if (orderDetaiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetaiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetaiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderDetaiDTO> result = orderDetaiService.partialUpdate(orderDetaiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDetaiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-detais} : get all the orderDetais.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetais in body.
     */
    @GetMapping("/order-detais")
    public ResponseEntity<List<OrderDetaiDTO>> getAllOrderDetais(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderDetais");
        Page<OrderDetaiDTO> page = orderDetaiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-detais/:id} : get the "id" orderDetai.
     *
     * @param id the id of the orderDetaiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetaiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-detais/{id}")
    public ResponseEntity<OrderDetaiDTO> getOrderDetai(@PathVariable Long id) {
        log.debug("REST request to get OrderDetai : {}", id);
        Optional<OrderDetaiDTO> orderDetaiDTO = orderDetaiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDetaiDTO);
    }

    /**
     * {@code DELETE  /order-detais/:id} : delete the "id" orderDetai.
     *
     * @param id the id of the orderDetaiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-detais/{id}")
    public ResponseEntity<Void> deleteOrderDetai(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetai : {}", id);
        orderDetaiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package co.edu.sena.web.rest;

import co.edu.sena.repository.WayToPayRepository;
import co.edu.sena.service.WayToPayService;
import co.edu.sena.service.dto.WayToPayDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.WayToPay}.
 */
@RestController
@RequestMapping("/api")
public class WayToPayResource {

    private final Logger log = LoggerFactory.getLogger(WayToPayResource.class);

    private static final String ENTITY_NAME = "wayToPay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WayToPayService wayToPayService;

    private final WayToPayRepository wayToPayRepository;

    public WayToPayResource(WayToPayService wayToPayService, WayToPayRepository wayToPayRepository) {
        this.wayToPayService = wayToPayService;
        this.wayToPayRepository = wayToPayRepository;
    }

    /**
     * {@code POST  /way-to-pays} : Create a new wayToPay.
     *
     * @param wayToPayDTO the wayToPayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wayToPayDTO, or with status {@code 400 (Bad Request)} if the wayToPay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/way-to-pays")
    public ResponseEntity<WayToPayDTO> createWayToPay(@RequestBody WayToPayDTO wayToPayDTO) throws URISyntaxException {
        log.debug("REST request to save WayToPay : {}", wayToPayDTO);
        if (wayToPayDTO.getId() != null) {
            throw new BadRequestAlertException("A new wayToPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WayToPayDTO result = wayToPayService.save(wayToPayDTO);
        return ResponseEntity
            .created(new URI("/api/way-to-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /way-to-pays/:id} : Updates an existing wayToPay.
     *
     * @param id the id of the wayToPayDTO to save.
     * @param wayToPayDTO the wayToPayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wayToPayDTO,
     * or with status {@code 400 (Bad Request)} if the wayToPayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wayToPayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/way-to-pays/{id}")
    public ResponseEntity<WayToPayDTO> updateWayToPay(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WayToPayDTO wayToPayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WayToPay : {}, {}", id, wayToPayDTO);
        if (wayToPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wayToPayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wayToPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WayToPayDTO result = wayToPayService.update(wayToPayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wayToPayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /way-to-pays/:id} : Partial updates given fields of an existing wayToPay, field will ignore if it is null
     *
     * @param id the id of the wayToPayDTO to save.
     * @param wayToPayDTO the wayToPayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wayToPayDTO,
     * or with status {@code 400 (Bad Request)} if the wayToPayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wayToPayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wayToPayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/way-to-pays/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WayToPayDTO> partialUpdateWayToPay(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WayToPayDTO wayToPayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WayToPay partially : {}, {}", id, wayToPayDTO);
        if (wayToPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wayToPayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wayToPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WayToPayDTO> result = wayToPayService.partialUpdate(wayToPayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wayToPayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /way-to-pays} : get all the wayToPays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wayToPays in body.
     */
    @GetMapping("/way-to-pays")
    public ResponseEntity<List<WayToPayDTO>> getAllWayToPays(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of WayToPays");
        Page<WayToPayDTO> page = wayToPayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /way-to-pays/:id} : get the "id" wayToPay.
     *
     * @param id the id of the wayToPayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wayToPayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/way-to-pays/{id}")
    public ResponseEntity<WayToPayDTO> getWayToPay(@PathVariable Long id) {
        log.debug("REST request to get WayToPay : {}", id);
        Optional<WayToPayDTO> wayToPayDTO = wayToPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wayToPayDTO);
    }

    /**
     * {@code DELETE  /way-to-pays/:id} : delete the "id" wayToPay.
     *
     * @param id the id of the wayToPayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/way-to-pays/{id}")
    public ResponseEntity<Void> deleteWayToPay(@PathVariable Long id) {
        log.debug("REST request to delete WayToPay : {}", id);
        wayToPayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

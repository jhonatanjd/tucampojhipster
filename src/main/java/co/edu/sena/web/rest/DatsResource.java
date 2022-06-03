package co.edu.sena.web.rest;

import co.edu.sena.repository.DatsRepository;
import co.edu.sena.service.DatsService;
import co.edu.sena.service.dto.DatsDTO;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link co.edu.sena.domain.Dats}.
 */
@RestController
@RequestMapping("/api")
public class DatsResource {

    private final Logger log = LoggerFactory.getLogger(DatsResource.class);

    private static final String ENTITY_NAME = "dats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatsService datsService;

    private final DatsRepository datsRepository;

    public DatsResource(DatsService datsService, DatsRepository datsRepository) {
        this.datsService = datsService;
        this.datsRepository = datsRepository;
    }

    /**
     * {@code POST  /dats} : Create a new dats.
     *
     * @param datsDTO the datsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datsDTO, or with status {@code 400 (Bad Request)} if the dats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dats")
    public ResponseEntity<DatsDTO> createDats(@Valid @RequestBody DatsDTO datsDTO) throws URISyntaxException {
        log.debug("REST request to save Dats : {}", datsDTO);
        if (datsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatsDTO result = datsService.save(datsDTO);
        return ResponseEntity
            .created(new URI("/api/dats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dats/:id} : Updates an existing dats.
     *
     * @param id the id of the datsDTO to save.
     * @param datsDTO the datsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datsDTO,
     * or with status {@code 400 (Bad Request)} if the datsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dats/{id}")
    public ResponseEntity<DatsDTO> updateDats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DatsDTO datsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dats : {}, {}", id, datsDTO);
        if (datsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DatsDTO result = datsService.update(datsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dats/:id} : Partial updates given fields of an existing dats, field will ignore if it is null
     *
     * @param id the id of the datsDTO to save.
     * @param datsDTO the datsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datsDTO,
     * or with status {@code 400 (Bad Request)} if the datsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the datsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the datsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DatsDTO> partialUpdateDats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DatsDTO datsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dats partially : {}, {}", id, datsDTO);
        if (datsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DatsDTO> result = datsService.partialUpdate(datsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dats} : get all the dats.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dats in body.
     */
    @GetMapping("/dats")
    public ResponseEntity<List<DatsDTO>> getAllDats(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("documentype-is-null".equals(filter)) {
            log.debug("REST request to get all Datss where documenType is null");
            return new ResponseEntity<>(datsService.findAllWhereDocumenTypeIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Dats");
        Page<DatsDTO> page = datsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dats/:id} : get the "id" dats.
     *
     * @param id the id of the datsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dats/{id}")
    public ResponseEntity<DatsDTO> getDats(@PathVariable Long id) {
        log.debug("REST request to get Dats : {}", id);
        Optional<DatsDTO> datsDTO = datsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datsDTO);
    }

    /**
     * {@code DELETE  /dats/:id} : delete the "id" dats.
     *
     * @param id the id of the datsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dats/{id}")
    public ResponseEntity<Void> deleteDats(@PathVariable Long id) {
        log.debug("REST request to delete Dats : {}", id);
        datsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package co.edu.sena.web.rest;

import co.edu.sena.repository.AnonymousRepository;
import co.edu.sena.service.AnonymousService;
import co.edu.sena.service.dto.AnonymousDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.Anonymous}.
 */
@RestController
@RequestMapping("/api")
public class AnonymousResource {

    private final Logger log = LoggerFactory.getLogger(AnonymousResource.class);

    private static final String ENTITY_NAME = "anonymous";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnonymousService anonymousService;

    private final AnonymousRepository anonymousRepository;

    public AnonymousResource(AnonymousService anonymousService, AnonymousRepository anonymousRepository) {
        this.anonymousService = anonymousService;
        this.anonymousRepository = anonymousRepository;
    }

    /**
     * {@code POST  /anonymous} : Create a new anonymous.
     *
     * @param anonymousDTO the anonymousDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anonymousDTO, or with status {@code 400 (Bad Request)} if the anonymous has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anonymous")
    public ResponseEntity<AnonymousDTO> createAnonymous(@RequestBody AnonymousDTO anonymousDTO) throws URISyntaxException {
        log.debug("REST request to save Anonymous : {}", anonymousDTO);
        if (anonymousDTO.getId() != null) {
            throw new BadRequestAlertException("A new anonymous cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnonymousDTO result = anonymousService.save(anonymousDTO);
        return ResponseEntity
            .created(new URI("/api/anonymous/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anonymous/:id} : Updates an existing anonymous.
     *
     * @param id the id of the anonymousDTO to save.
     * @param anonymousDTO the anonymousDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anonymousDTO,
     * or with status {@code 400 (Bad Request)} if the anonymousDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anonymousDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anonymous/{id}")
    public ResponseEntity<AnonymousDTO> updateAnonymous(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnonymousDTO anonymousDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Anonymous : {}, {}", id, anonymousDTO);
        if (anonymousDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anonymousDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anonymousRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnonymousDTO result = anonymousService.update(anonymousDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anonymousDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /anonymous/:id} : Partial updates given fields of an existing anonymous, field will ignore if it is null
     *
     * @param id the id of the anonymousDTO to save.
     * @param anonymousDTO the anonymousDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anonymousDTO,
     * or with status {@code 400 (Bad Request)} if the anonymousDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anonymousDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anonymousDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/anonymous/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnonymousDTO> partialUpdateAnonymous(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnonymousDTO anonymousDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Anonymous partially : {}, {}", id, anonymousDTO);
        if (anonymousDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anonymousDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anonymousRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnonymousDTO> result = anonymousService.partialUpdate(anonymousDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anonymousDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anonymous} : get all the anonymous.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anonymous in body.
     */
    @GetMapping("/anonymous")
    public ResponseEntity<List<AnonymousDTO>> getAllAnonymous(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Anonymous");
        Page<AnonymousDTO> page = anonymousService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anonymous/:id} : get the "id" anonymous.
     *
     * @param id the id of the anonymousDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anonymousDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anonymous/{id}")
    public ResponseEntity<AnonymousDTO> getAnonymous(@PathVariable Long id) {
        log.debug("REST request to get Anonymous : {}", id);
        Optional<AnonymousDTO> anonymousDTO = anonymousService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anonymousDTO);
    }

    /**
     * {@code DELETE  /anonymous/:id} : delete the "id" anonymous.
     *
     * @param id the id of the anonymousDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anonymous/{id}")
    public ResponseEntity<Void> deleteAnonymous(@PathVariable Long id) {
        log.debug("REST request to delete Anonymous : {}", id);
        anonymousService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

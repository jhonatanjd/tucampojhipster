package co.edu.sena.web.rest;

import co.edu.sena.repository.DocumenTypeRepository;
import co.edu.sena.service.DocumenTypeService;
import co.edu.sena.service.dto.DocumenTypeDTO;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link co.edu.sena.domain.DocumenType}.
 */
@RestController
@RequestMapping("/api")
public class DocumenTypeResource {

    private final Logger log = LoggerFactory.getLogger(DocumenTypeResource.class);

    private static final String ENTITY_NAME = "documenType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumenTypeService documenTypeService;

    private final DocumenTypeRepository documenTypeRepository;

    public DocumenTypeResource(DocumenTypeService documenTypeService, DocumenTypeRepository documenTypeRepository) {
        this.documenTypeService = documenTypeService;
        this.documenTypeRepository = documenTypeRepository;
    }

    /**
     * {@code POST  /documen-types} : Create a new documenType.
     *
     * @param documenTypeDTO the documenTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documenTypeDTO, or with status {@code 400 (Bad Request)} if the documenType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documen-types")
    public ResponseEntity<DocumenTypeDTO> createDocumenType(@Valid @RequestBody DocumenTypeDTO documenTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DocumenType : {}", documenTypeDTO);
        if (documenTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new documenType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumenTypeDTO result = documenTypeService.save(documenTypeDTO);
        return ResponseEntity
            .created(new URI("/api/documen-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documen-types/:id} : Updates an existing documenType.
     *
     * @param id the id of the documenTypeDTO to save.
     * @param documenTypeDTO the documenTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documenTypeDTO,
     * or with status {@code 400 (Bad Request)} if the documenTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documenTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documen-types/{id}")
    public ResponseEntity<DocumenTypeDTO> updateDocumenType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumenTypeDTO documenTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DocumenType : {}, {}", id, documenTypeDTO);
        if (documenTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documenTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documenTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumenTypeDTO result = documenTypeService.update(documenTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documenTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /documen-types/:id} : Partial updates given fields of an existing documenType, field will ignore if it is null
     *
     * @param id the id of the documenTypeDTO to save.
     * @param documenTypeDTO the documenTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documenTypeDTO,
     * or with status {@code 400 (Bad Request)} if the documenTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documenTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documenTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/documen-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumenTypeDTO> partialUpdateDocumenType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumenTypeDTO documenTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumenType partially : {}, {}", id, documenTypeDTO);
        if (documenTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documenTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documenTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumenTypeDTO> result = documenTypeService.partialUpdate(documenTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documenTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /documen-types} : get all the documenTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documenTypes in body.
     */
    @GetMapping("/documen-types")
    public ResponseEntity<List<DocumenTypeDTO>> getAllDocumenTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DocumenTypes");
        Page<DocumenTypeDTO> page = documenTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documen-types/:id} : get the "id" documenType.
     *
     * @param id the id of the documenTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documenTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documen-types/{id}")
    public ResponseEntity<DocumenTypeDTO> getDocumenType(@PathVariable Long id) {
        log.debug("REST request to get DocumenType : {}", id);
        Optional<DocumenTypeDTO> documenTypeDTO = documenTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documenTypeDTO);
    }

    /**
     * {@code DELETE  /documen-types/:id} : delete the "id" documenType.
     *
     * @param id the id of the documenTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documen-types/{id}")
    public ResponseEntity<Void> deleteDocumenType(@PathVariable Long id) {
        log.debug("REST request to delete DocumenType : {}", id);
        documenTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

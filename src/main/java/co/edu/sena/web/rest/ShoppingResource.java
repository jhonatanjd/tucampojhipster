package co.edu.sena.web.rest;

import co.edu.sena.repository.ShoppingRepository;
import co.edu.sena.service.ShoppingService;
import co.edu.sena.service.dto.ShoppingDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.Shopping}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingResource.class);

    private static final String ENTITY_NAME = "shopping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingService shoppingService;

    private final ShoppingRepository shoppingRepository;

    public ShoppingResource(ShoppingService shoppingService, ShoppingRepository shoppingRepository) {
        this.shoppingService = shoppingService;
        this.shoppingRepository = shoppingRepository;
    }

    /**
     * {@code POST  /shoppings} : Create a new shopping.
     *
     * @param shoppingDTO the shoppingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingDTO, or with status {@code 400 (Bad Request)} if the shopping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoppings")
    public ResponseEntity<ShoppingDTO> createShopping(@Valid @RequestBody ShoppingDTO shoppingDTO) throws URISyntaxException {
        log.debug("REST request to save Shopping : {}", shoppingDTO);
        if (shoppingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingDTO result = shoppingService.save(shoppingDTO);
        return ResponseEntity
            .created(new URI("/api/shoppings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shoppings/:id} : Updates an existing shopping.
     *
     * @param id the id of the shoppingDTO to save.
     * @param shoppingDTO the shoppingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoppings/{id}")
    public ResponseEntity<ShoppingDTO> updateShopping(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShoppingDTO shoppingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Shopping : {}, {}", id, shoppingDTO);
        if (shoppingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoppingDTO result = shoppingService.update(shoppingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shoppings/:id} : Partial updates given fields of an existing shopping, field will ignore if it is null
     *
     * @param id the id of the shoppingDTO to save.
     * @param shoppingDTO the shoppingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoppingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoppingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoppings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoppingDTO> partialUpdateShopping(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShoppingDTO shoppingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Shopping partially : {}, {}", id, shoppingDTO);
        if (shoppingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoppingDTO> result = shoppingService.partialUpdate(shoppingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoppings} : get all the shoppings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppings in body.
     */
    @GetMapping("/shoppings")
    public ResponseEntity<List<ShoppingDTO>> getAllShoppings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Shoppings");
        Page<ShoppingDTO> page = shoppingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shoppings/:id} : get the "id" shopping.
     *
     * @param id the id of the shoppingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoppings/{id}")
    public ResponseEntity<ShoppingDTO> getShopping(@PathVariable Long id) {
        log.debug("REST request to get Shopping : {}", id);
        Optional<ShoppingDTO> shoppingDTO = shoppingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingDTO);
    }

    /**
     * {@code DELETE  /shoppings/:id} : delete the "id" shopping.
     *
     * @param id the id of the shoppingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoppings/{id}")
    public ResponseEntity<Void> deleteShopping(@PathVariable Long id) {
        log.debug("REST request to delete Shopping : {}", id);
        shoppingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

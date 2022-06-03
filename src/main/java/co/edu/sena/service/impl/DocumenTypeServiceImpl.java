package co.edu.sena.service.impl;

import co.edu.sena.domain.DocumenType;
import co.edu.sena.repository.DocumenTypeRepository;
import co.edu.sena.service.DocumenTypeService;
import co.edu.sena.service.dto.DocumenTypeDTO;
import co.edu.sena.service.mapper.DocumenTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocumenType}.
 */
@Service
@Transactional
public class DocumenTypeServiceImpl implements DocumenTypeService {

    private final Logger log = LoggerFactory.getLogger(DocumenTypeServiceImpl.class);

    private final DocumenTypeRepository documenTypeRepository;

    private final DocumenTypeMapper documenTypeMapper;

    public DocumenTypeServiceImpl(DocumenTypeRepository documenTypeRepository, DocumenTypeMapper documenTypeMapper) {
        this.documenTypeRepository = documenTypeRepository;
        this.documenTypeMapper = documenTypeMapper;
    }

    @Override
    public DocumenTypeDTO save(DocumenTypeDTO documenTypeDTO) {
        log.debug("Request to save DocumenType : {}", documenTypeDTO);
        DocumenType documenType = documenTypeMapper.toEntity(documenTypeDTO);
        documenType = documenTypeRepository.save(documenType);
        return documenTypeMapper.toDto(documenType);
    }

    @Override
    public DocumenTypeDTO update(DocumenTypeDTO documenTypeDTO) {
        log.debug("Request to save DocumenType : {}", documenTypeDTO);
        DocumenType documenType = documenTypeMapper.toEntity(documenTypeDTO);
        documenType = documenTypeRepository.save(documenType);
        return documenTypeMapper.toDto(documenType);
    }

    @Override
    public Optional<DocumenTypeDTO> partialUpdate(DocumenTypeDTO documenTypeDTO) {
        log.debug("Request to partially update DocumenType : {}", documenTypeDTO);

        return documenTypeRepository
            .findById(documenTypeDTO.getId())
            .map(existingDocumenType -> {
                documenTypeMapper.partialUpdate(existingDocumenType, documenTypeDTO);

                return existingDocumenType;
            })
            .map(documenTypeRepository::save)
            .map(documenTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumenTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumenTypes");
        return documenTypeRepository.findAll(pageable).map(documenTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumenTypeDTO> findOne(Long id) {
        log.debug("Request to get DocumenType : {}", id);
        return documenTypeRepository.findById(id).map(documenTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumenType : {}", id);
        documenTypeRepository.deleteById(id);
    }
}

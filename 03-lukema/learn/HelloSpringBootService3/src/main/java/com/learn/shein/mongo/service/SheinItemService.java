package com.learn.shein.mongo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learn.shein.mongo.model.SheinItem;
import com.learn.shein.mongo.repository.SheinItemRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class SheinItemService {

    private final SheinItemRepository sheinItemRepository;

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok

    /**
    * !!! Trick !!!
    * `AbstractMongoEventListener<Person>` is used to set new document id automatically.
    * public class PersonModelListener extends AbstractMongoEventListener<Person>
    */
    public SheinItem insertSheinItem(SheinItem item) {
        // Long seq = this.personSeqGeneratorService.generateSequence(Person.SEQ_NAME);   <=== not needed with `AbstractMongoEventListener<Person>`
        // person.setId(seq);   <=== not needed with `AbstractMongoEventListener<Person>`

        return this.sheinItemRepository.insert(item);
    }

    public List<SheinItem> getAll() {
        return this.sheinItemRepository.findAll();
    }

    public List<SheinItem> findBySellerUsername(String sellerUsername) {
        return this.sheinItemRepository.findBySellerUsername(sellerUsername);
    }

    public Optional<SheinItem> findById(Long id) {
        return this.sheinItemRepository.findById(id);
    }

    public Optional<SheinItem> updateStatusById(Long id, String status) {
        Optional<SheinItem> opt = this.sheinItemRepository.findById(id);
        if (opt.isPresent()) {
            SheinItem item = opt.get();
            item.setStatus(status);
            item = this.sheinItemRepository.save(item);
            opt = Optional.of(item);
        }
        return opt;
    }

    public SheinItem updateById(Long id, SheinItem item) {
        Optional<SheinItem> optionalItem = this.sheinItemRepository.findById(id);
        if (optionalItem.isPresent()) {
            SheinItem old = optionalItem.get();
            old.setId(item.getId());
            old.setTitle(item.getTitle());
            return this.sheinItemRepository.save(old);
        } else {
            throw new RuntimeException(String.format("SheinItem with id %d not found", id));
        }
    }

    public void deleteById(Long id) {
        this.sheinItemRepository.deleteById(id);
    }

    public List<SheinItem> findByTitle(String title) {
        return this.sheinItemRepository.findByTitle(title);
    }

    public List<SheinItem> findByNameContainingIgnoreCase(String title) {
        return this.sheinItemRepository.findByNameContainingIgnoreCase(title);
    }

    public SheinItem save(SheinItem item) {
        return this.sheinItemRepository.save(item);
    }
}

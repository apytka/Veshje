package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateStoreDTO;
import com.agatap.veshje.controller.DTO.CreateUpdateTokenDTO;
import com.agatap.veshje.controller.DTO.StoreDTO;
import com.agatap.veshje.controller.DTO.TokenDTO;
import com.agatap.veshje.service.TokenService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @GetMapping
    public List<TokenDTO> getAllTokens() {
        return tokenService.getAllTokens();
    }

    @GetMapping("/{id}")
    public TokenDTO findTokenDTOById(@PathVariable Integer id) throws TokenNotFoundException {
        return tokenService.findTokenDTOById(id);
    }

    @PostMapping
    public TokenDTO createTokenDTO(@RequestBody CreateUpdateTokenDTO createUpdateTokenDTO) throws TokenNotFoundException, UserNotFoundException {
        return tokenService.createTokenDTO(createUpdateTokenDTO);
    }

    @PutMapping("/{id}")
    public TokenDTO updateTokenDTO(@RequestBody CreateUpdateTokenDTO createUpdateTokenDTO, @PathVariable Integer id) throws TokenNotFoundException {
        return tokenService.updateTokenDTO(createUpdateTokenDTO, id);
    }

    @DeleteMapping("/{id}")
    public TokenDTO deleteTokenDTO(@PathVariable Integer id) throws TokenNotFoundException {
        return tokenService.deleteTokenDTO(id);
    }
}

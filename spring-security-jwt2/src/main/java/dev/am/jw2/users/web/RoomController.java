package dev.am.jw2.users.web;

import dev.am.jw2.dto.RoomResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
class RoomController {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    String createRoom() {
        return "Created room";
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    String rooms() {
        return "All rooms";
    }

    @GetMapping("/{roomId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostAuthorize("returnObject.username == authentication.name")
    RoomResponse room(@PathVariable int roomId) {
        return new RoomResponse(roomId, "Romm", "amerida");
    }
}

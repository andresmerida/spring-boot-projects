package dev.am.jw2.users.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
class RoomController {

    @GetMapping
    String rooms() {
        return "All rooms";
    }

    @GetMapping("/{roomId}")
    String room(@PathVariable Integer roomId) {
        return "Room " + roomId;
    }

    @PostMapping
    String createRoom() {
        return "Created room";
    }
}

package teikyo.mcc.CampusChase.data;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    private Map<String, List<Integer>> roomUsers = new HashMap<>();

    // ルーム作成
    @PostMapping
    public Map<String, Object> createRoom() {
        String roomId = UUID.randomUUID().toString().substring(0, 6);

        roomUsers.put(roomId, new ArrayList<>());

        Map<String, Object> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("message", "ルームを作成しました");

        return response;
    }

    // ルーム情報取得
    @GetMapping("/{roomId}")
    public Map<String, Object> getRoom(@PathVariable String roomId) {
        Map<String, Object> response = new HashMap<>();

        response.put("roomId", roomId);
        response.put("users", roomUsers.getOrDefault(roomId, new ArrayList<>()));

        return response;
    }

    // ルーム参加
    @PostMapping("/{roomId}/join")
    public Map<String, Object> joinRoom(
            @PathVariable String roomId,
            @RequestBody Map<String, Integer> request
    ) {
        Integer userId = request.get("userId");

        roomUsers.putIfAbsent(roomId, new ArrayList<>());
        roomUsers.get(roomId).add(userId);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userId);
        userMap.put("team", null);

        AdminController.addUser(roomId, userMap);

        Map<String, Object> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("userId", userId);
        response.put("message", "ルームに参加しました");

        return response;
    }

    // 参加ユーザー一覧取得
    @GetMapping("/{roomId}/users")
    public List<Integer> getRoomUsers(@PathVariable String roomId) {
        return roomUsers.getOrDefault(roomId, new ArrayList<>());
    }
}
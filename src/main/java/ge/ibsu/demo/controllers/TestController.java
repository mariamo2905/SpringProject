package ge.ibsu.demo.controllers;

import ge.ibsu.demo.dto.TestPerson;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hi")
    public String sayHi() {
        return "Hello From Server";
    }

    @PostMapping("/call")
    public TestPerson testCall(@RequestBody TestPerson p) {
        p.setFirstName(p.getFirstName() + " from server");
        return p;
    }
}

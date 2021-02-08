package murad.crud.controller;

import murad.crud.exception.IdNotFoundException;
import murad.crud.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import murad.crud.repository.EmployeeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return this.employeeRepository.findAll();
    }


    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws IdNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
               .orElseThrow(() -> new IdNotFoundException("Employee not found for this id ::" + employeeId));
       return ResponseEntity.ok().body(employee);

    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) { //требует что именно отправить в запросе (отправит объект employee)
        return this.employeeRepository.save(employee);
    }


    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @RequestBody Employee employeeDetails) throws IdNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IdNotFoundException("Employee not found for this id ::" + employeeId));

        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws IdNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IdNotFoundException("Employee not found for this id ::" + employeeId));

            this.employeeRepository.delete(employee);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);

            return  response;
    }

}

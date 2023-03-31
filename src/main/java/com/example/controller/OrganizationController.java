package com.example.controller;

import com.example.model.Organization;
import com.example.model.User;
import com.example.repository.OrganizationRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping("/org/all")
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }


    @GetMapping("/org/searchby/name/{name}")
    public ResponseEntity<?> getOrganizationsByName(@PathVariable(value = "name") String name) {
        List<Organization> organizations = organizationRepository.findByNameIgnoreCase(name);

        if (organizations.isEmpty()){
            String message = "No organizations found with the name: " + name;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(organizations);
    }



   // public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
    @GetMapping("/org/searchby/id/{id}")
    public ResponseEntity<Organization> getorganizationById(@PathVariable(value = "id") Long organizationId)
            throws ResourceNotFoundException {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id :: " + organizationId));
        return ResponseEntity.ok().body(organization);
    }

    @PostMapping("/org/add")
    //public Employee createEmployee(@RequestBody User user) {
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization)
    {
        Organization savedOrganization = organizationRepository.save(organization);
        return ResponseEntity.created(null).body(savedOrganization);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable(value = "id") Long organizationId,
                                               @RequestBody Organization organizationDetails) throws ResourceNotFoundException {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + organizationId));

        organization.setfirstName(organizationDetails.getfirstName());
        organization.setmiddleName(organizationDetails.getmiddleName());
        organization.setlastName(organizationDetails.getlastName());
        organization.setmobilePhone(organizationDetails.getmobilePhone());
        organization.setworkPhone(organizationDetails.getworkPhone());
        organization.sethomePhone(organizationDetails.gethomePhone());
        organization.setEmail(organizationDetails.getEmail());

        organization.setcountryId(organizationDetails.getcountryId());

        organization.setstatusId(organizationDetails.getstatusId());
        organization.setcreatedOn(organizationDetails.getcreatedOn());
        organization.setcreatedBy(organizationDetails.getcreatedBy());
        organization.setmodifiedOn(organizationDetails.getmodifiedOn());
        organization.setmodifiedBy(organizationDetails.getmodifiedBy());
        organization.setrowVersion(organizationDetails.getrowVersion());

        final Organization updatedOrganization = organizationRepository.save(organization);
        return ResponseEntity.ok(updatedOrganization);
    }

    @DeleteMapping("/org/delete/{id}")
    public Map<String, Boolean> deleteOrganization(@PathVariable(value = "id") Long organizationId)
            throws ResourceNotFoundException {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id :: " + organizationId));

        organizationRepository.delete(organization);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
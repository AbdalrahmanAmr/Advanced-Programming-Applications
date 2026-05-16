package dev.mr3.sb.controller;

import dev.mr3.sb.model.Certificate;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Person;
import dev.mr3.sb.repository.PersonRepository;
import dev.mr3.sb.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/profile-picture/{personId}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long personId) {
        Person person = personRepository.findById(personId).orElse(null);
        if (person != null && person.getProfilePicture() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(person.getProfilePictureContentType()));
            return new ResponseEntity<>(person.getProfilePicture(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/certificate/{doctorId}/{certIndex}")
    public ResponseEntity<byte[]> getCertificate(@PathVariable Long doctorId, @PathVariable int certIndex) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor != null && doctor.getCertificates() != null && doctor.getCertificates().size() > certIndex) {
            Certificate cert = doctor.getCertificates().get(certIndex);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(cert.getContentType()));
            headers.setContentDispositionFormData("attachment", cert.getFileName());
            return new ResponseEntity<>(cert.getData(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

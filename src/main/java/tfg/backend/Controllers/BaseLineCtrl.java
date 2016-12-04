/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the baseLine management
 * includes activities and milestones.
 * End-points:
 */
package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.*;
import tfg.backend.DataModel.Ids.MilestoneID;
import tfg.backend.RequestsStructures.BaseLineData;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BaseLineCtrl {

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private UserDao userDaoo;

    @Autowired
    private SubjectDao subjectDaoo;

    @Autowired
    private BaseLineDao baseLineDao;

    @Autowired
    private MilestoneDao milestoneDao;

    @Autowired
    private AccessDao accessDao;

    /**
     * @param data
     * @param headers
     * @return
     */
    @RequestMapping(value = "/baseLine", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseLineData> createNewBaseLine(
            @RequestBody BaseLineData data, @RequestHeader HttpHeaders headers) {

        BaseLineData baseLine = new BaseLineData();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {

            // First, checks if the user have permisions for this end-points.
            // It's necesary look at the study permision.

            Subject aux = subjectDaoo.findSubject(
                    data.getBaseLine().getNumSubjectOne().getNumSubject());
            if (Auth.isAuthenticated(aux.
                    getStudyName().getStudyName(), headers, userDaoo)) {

                // First save baseLine.
                Long id = baseLineDao.saveBaseLine(data.getBaseLine());
                data.getBaseLine().setId(id);

                for (Milestone a : data.getMilestones()) {
                    MilestoneID milestoneID = new MilestoneID();
                    milestoneID.setId(data.getBaseLine());
                    a.setId(milestoneID);
                    milestoneDao.saveMilestone(a);
                }
                code = HttpStatus.OK;
                baseLine = data;

            } else {
                code = HttpStatus.UNAUTHORIZED;

            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDaoo, headers, "POST",
                    code.value(), "/baseLine");
            return new ResponseEntity<BaseLineData>(baseLine, code);
        }
    }

    @RequestMapping(value = "/baseLine/milestones/{identifier}",
            method = RequestMethod.GET)
    public ResponseEntity<List<Milestone>> findSeriesOfCognitiveTest(
            @PathVariable(value = "identifier") long id,
            @RequestHeader HttpHeaders headers) {

        List<Milestone> list = new ArrayList<>();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {
            BaseLine baseLine = baseLineDao.findBaseLine(id);

            if (baseLine != null) {
                Subject subject = subjectDaoo.findSubject(
                        baseLine.getNumSubjectOne().getNumSubject());
                list = milestoneDao.findByBaseLine(id);
                if (Auth.isAuthenticated(
                        subject.getStudyName().getStudyName(), headers, userDaoo)) {
                    code = HttpStatus.OK;

                } else {

                    code = HttpStatus.UNAUTHORIZED;
                }

            } else {
                code = HttpStatus.NOT_FOUND;
            }

        } catch (Exception e) {

            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDaoo, headers, "GET",
                    code.value(), "/baseLine/milestones/" + id);
            return new ResponseEntity<List<Milestone>>(list, code);
        }
    }
}
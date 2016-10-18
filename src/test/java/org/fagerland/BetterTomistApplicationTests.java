package org.fagerland;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BetterTomistApplicationTests {

	@Test
	public void contextLoads() {
	}

	Student alan, ellie, ian, john, tim, lex;

    @Autowired
    StudentRepository studentRepo;

    Subject dna, paleontology, running, paleobotany, chaos, rescue, flying, unix;

    @Autowired
    SubjectRepository subjectRepo;

    Class a1, a2, a3;

    @Autowired
    ClassRepository classRepo;

    Group bio, handy, tech, advanced;

    @Autowired
    GroupRepository groupRepo;

    @Before
    public void setUp() throws Exception {

        // Wipe repositories
        studentRepo.deleteAll();
        groupRepo.deleteAll();
        subjectRepo.deleteAll();
        classRepo.deleteAll();

        // Create test students
        alan = new Student("Alan Grant", 2);
        ellie = new Student("Ellie Sattler", 2);
        ian = new Student("Ian Malcolm", 1);
        john = new Student("John Hammond", 1);
        tim = new Student("Tim Murphy", 3);
        lex = new Student("Alexis Murphy",3);

        // Insert students into persisted storage
        studentRepo.save(Arrays.asList(alan, ellie, ian, john, tim, lex));

        // Create test subjects

        dna = new Subject("Generating Dinosaurs from extinct DNA.");
        paleontology = new Subject("Dinosaurs. What are they like?");
        running = new Subject("Running from Dinosaurs.");
        paleobotany = new Subject("Ancient plants, and which dinosaurs eats them... Or something.");
        chaos = new Subject("Chaos Theory and advanced maths.");
        rescue = new Subject("Advanced rescue tactics.");
        flying = new Subject("Helicopter flying for beginners.");
        unix = new Subject("Unix systems. She knows these.");

        // Insert students into persisted storage
        subjectRepo.save(Arrays.asList(dna, paleontology, paleobotany, running, chaos, rescue, flying, unix));

        // Create test classes

        a1 = new Class("1A");
        a2 = new Class("2A");
        a3 = new Class("3A");

        // Insert classes into persistent storage
        classRepo.save(Arrays.asList(a1, a2, a3));

        // Create test groups
        // Note that the default values of
        // minStudents and maxStudents are both 2, but
        // This group is REALLY important, so even if it only has one student, it should be taught.
        handy = new Group("Handy Subjects", 1, 4, running, rescue, flying);
        tech = new Group("Advanced technologies", dna, unix);
        advanced = new Group("Dynamic Maths", chaos);
        // This group takes up a lot of resources, so it will not be taught unless it has at least six students
        bio = new Group("Biology", 6, 12, paleobotany, paleontology);


        // Insert groups
        groupRepo.save(Arrays.asList(bio, handy, tech, advanced));

        /*
        Assign students to subjects
        As they are added, groups are located, and students added to it.
        If group is full, student is unplaced.
         */
        alan.addSubject(paleontology, bio);
        alan.addSubject(running, handy);
        ellie.addSubject(paleobotany, bio);
        ellie.addSubject(running, handy);
        tim.addSubject(paleontology, bio);
        tim.addSubject(running, handy);
        lex.addSubject(unix, tech);
        lex.addSubject(running, handy);
        ian.addSubject(chaos, advanced);
        john.addSubject(dna, tech);
        john.addSubject(paleontology, bio);
    }


// Unit tests:
    // Student
    @Test
    public void studentRepoWasReset() throws Exception {
        long expected = 6; // We know there are six students, because we created and inserted them manually
        long actual = studentRepo.count();
        assertEquals(expected, actual);
    }

    @Test
    public void canFindStudentById() throws Exception {
        // Using alan's ID, we look for his record in the persisted storage
        Student expectedStudent = (Student) studentRepo.findOne(alan.getId());
        // Since we can't compare the objects directly, we have to settle for comparing the accessible data.
        assertEquals(alan.getName(), expectedStudent.getName());
        assertEquals(alan.getId(), expectedStudent.getId());
    }

    @Test
    public void canFindStudentByName() throws Exception {
        // When searching for Ellie by name, we still find her in the persisted storage
        Student expectedStudent = studentRepo.findByName(ellie.getName());
        // Hey, look! A shortcut which simultaneously tests all data fields!
        assertEquals(ellie.toString(), expectedStudent.toString());
    }

    @Test
    public void setName() throws Exception {
        // Some times students need to change names... I think. Seems like it should be available.
        String expectedName = "Ellie Grant"; // Headcanon: after the movie, Alan and Ellie get married.
        Student expectedStudent = studentRepo.findOne(ellie.getId());
        expectedStudent.setName(expectedName);

        // Name got saved locally
        assertEquals(expectedName, expectedStudent.getName());

        // Now we save it to persisted storage
        studentRepo.save(expectedStudent);
        // Searching by the new name should therefor yield exactly the same student.
        Student actualStudent = studentRepo.findByName(expectedName);
        assertEquals(expectedName, actualStudent.getName());
    }
    @Test
    public void addSubject(){
        assertEquals(true,lex.getSubjects().contains(unix));
        assertEquals(true, lex.getGroups().contains(tech));
    }
    // No new methods in Class and Subject, so it's not necessary to unit test those classes.
    // Group
    @Test
    public void hasSpace() throws Exception{
        boolean techFree = tech.hasSpace();
        boolean handyFree = handy.hasSpace();
        // There should only be two students in this class, Lex and Ian. The max is set to three.
        assertEquals(true, techFree);
        //
        assertEquals(false, handyFree);
    }
    @Test
    public void hasEnoughStudents() throws Exception{
        boolean techWillBeTaught = tech.hasEnoughStudents(); // true
        boolean bioWillBeTaught = bio.hasEnoughStudents(); // false
        assertEquals(true, techWillBeTaught);
        assertEquals(false, bioWillBeTaught);
    }
    // groupRepo
    @Test
    public void findGroupBySubject() throws Exception{
        Group expected = tech;
        // There is only one group learning "Generating Dinosaurs from extinct DNA"
        List<Group> actualGroups = groupRepo.findBySubjects(dna);
        Group actual = actualGroups.get(0);
        assertEquals(actual.getClass(), expected.getClass());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
    }

}

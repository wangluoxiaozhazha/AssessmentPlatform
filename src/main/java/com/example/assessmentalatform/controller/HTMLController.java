package com.example.assessmentalatform.controller;

import com.example.assessmentalatform.bean.Course;
import com.example.assessmentalatform.bean.Curriculum;
import com.example.assessmentalatform.bean.Student;
import com.example.assessmentalatform.bean.Teacher;
import com.example.assessmentalatform.mapper.CourseMapper;
import com.example.assessmentalatform.mapper.CurriculumMapper;
import com.example.assessmentalatform.mapper.StudentMapper;
import com.example.assessmentalatform.mapper.TeacherMapper;
import com.example.assessmentalatform.tool.EchartsDataProcessing;
import com.example.assessmentalatform.tool.Transmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class HTMLController {

    @Autowired
    Transmission transmission;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CurriculumMapper curriculumMapper;
    @Autowired
    CourseMapper courseMapper;
    String admin_menu="admin_menu";
    @GetMapping("/admin/index")
    public String getIndexAdmin(Model model){//管理员界面
        Integer teacherNumber=teacherMapper.selectTeacherNumber();
        Integer stuNumber=studentMapper.selectStuNumber();
        Integer courseNumber=courseMapper.selectCourseNumber();
        int[] datas={14,16,16,18,11,10};
        String[] datas2={"一月","二月","三月","四月","五月","六月"};
        EchartsDataProcessing echartsDataProcessing=new EchartsDataProcessing();
        String data=echartsDataProcessing.getFormatData(datas);
        String data1=echartsDataProcessing.getFormatData(datas2);
        model.addAttribute("teacherNumber",teacherNumber);
        model.addAttribute("stuNumber",stuNumber);
        model.addAttribute("courseNumber",courseNumber);
        model.addAttribute("data",data);
        model.addAttribute("data1",data1);
        System.out.println(data1);
        System.out.println(data);
        return "index-administrator";
    }
    @GetMapping("/admin/teacher")
    public String getTeacherByAdmin(Model model){

        transmission.initialization(teacherMapper.selectTeacherNumber());
        model.addAttribute("transmission",transmission);
        Collection<Teacher> teachers=teacherMapper.selectTeacherByPage((transmission.getPage()-1)*10);
        model.addAttribute("teachers",teachers);
        return "teacher-information";
    }
    @GetMapping("/admin/stu")
    public String getStuByAdmin(Model model){

        transmission.initialization(studentMapper.selectStuNumber());
        transmission.setMenu(admin_menu);
        model.addAttribute("transmission",transmission);
        Collection<Student> students=studentMapper.selectStuByPage((transmission.getPage()-1)*10);
        model.addAttribute("students",students);
        model.addAttribute("url","/admin/data/page");
        return "student-information";
    }
    @GetMapping("/admin/curriculum")
    public  String getCurriculumByadmin(Model model){
        transmission.initialization(curriculumMapper.selectCurriculumNumber());
        transmission.setMenu(admin_menu);
        model.addAttribute("transmission",transmission);
        Collection<Curriculum> curriculums=curriculumMapper.selectCurriculumByPage((transmission.getPage()-1)*10);
        model.addAttribute("curriculums",curriculums);
        return "curriculum-information";
    }
    @GetMapping("/admin/course")
    public String getClassByAdmin(Model model){
        transmission.initialization(courseMapper.selectCourseNumber());
        model.addAttribute("transmission",transmission);
        Collection<Course> courses=courseMapper.selectCourseByPage((transmission.getPage()-1)*10);
        model.addAttribute("courses",courses);
        return "course-information";
    }
    @GetMapping("/admin/file/request")
    public String getClassFileByAdmin(){
        return "file-request";
    }
    @GetMapping("/admin/file/upload")
    public String goFileUoload(@RequestParam("no") Integer no, Model model){
        model.addAttribute("no",no);
        if (no==1)
            model.addAttribute("classification","教师");
        else if(no==2)
            model.addAttribute("classification","学生");
        else if (no==3)
            model.addAttribute("classification","课目");
        else
            model.addAttribute("classification","课表");
        return "file-uoload";
    }
    ///////////////////////////////////////////////////////////////////////
    @GetMapping("/stu/index")
    public String getIndexStu(){
        return "index-student";
    }
///////////////////////////////////////////////////////////////////////////////
    @GetMapping("/teacher/index")
    public String getIndexTeacher(){
        return "index-teacher";
    }
}

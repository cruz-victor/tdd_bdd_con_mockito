package com.example.tddterminal.service;

import com.example.tddterminal.model.Terminal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TerminalServiceTest {
    @Test
    void should_look_a_terminal_without_parameters(){
        //GIVEN
        TerminalService terminalService=new TerminalService();

        //WHEN
        List<Terminal> terminals= terminalService.findTerminalByName();

        //THEN
        Assertions.assertFalse(terminals.isEmpty());
    }

    @Test
    void should_look_a_terminal_with_parameter(){
        //GIVEN
        TerminalService terminalService=new TerminalService();
        String terminalName="Terminal LP";
        //WHEN
        List<Terminal> terminals= terminalService.findTerminalByName(terminalName);
        //THEN
        Assertions.assertFalse(terminals.isEmpty());
    }

    @Test
    void should_create_a_terminal(){
        //GIVEN
        TerminalService terminalService=new TerminalService();
        Terminal newTerminal=new Terminal(1L,"Terminal LP");
        Terminal expectedTerminal=new Terminal(1L,"Terminal LP");
        //WHEN
        Terminal createdTerminal=terminalService.createTerminal(newTerminal);
        //THEN
        Assertions.assertEquals(expectedTerminal, createdTerminal);
    }

    @Test
    void should_modify_a_terminal(){
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    void should_remove_a_termianl(){
        //GIVEN
        //WHEN
        //THEN
    }
}

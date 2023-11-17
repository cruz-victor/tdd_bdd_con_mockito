package com.example.tddterminal.service;

import com.example.tddterminal.model.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TerminalService {
    private List<Terminal> terminals;

    public TerminalService(){
        terminals=new ArrayList<>();
        terminals.add(new Terminal(1L,"Terminal LP"));
        terminals.add(new Terminal(2L,"Terminal TJ"));
        terminals.add(new Terminal(3L,"Terminal SC"));
    }


    public List<Terminal> findTerminalByName() {
        //throw new UnsupportedOperationException("Not supported yet");
        return terminals;
    }

    public List<Terminal> findTerminalByName(String terminalName) {
        //throw new UnsupportedOperationException("No supported yet");
        return terminals.stream()
                .filter(terminal-> terminal.getName().equals(terminalName))
                .collect(Collectors.toList());
    }

    public Terminal createTerminal(Terminal newTerminal) {
        //throw new UnsupportedOperationException("No supported yet");
        terminals.add(newTerminal);
        return findTerminalByName(newTerminal.getName()).get(0);
    }
}

package com.example.tddterminal.service;

import com.example.tddterminal.exception.ElementNotFoundException;
import com.example.tddterminal.exception.ElementWasNotRemoved;
import com.example.tddterminal.model.Terminal;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class TerminalService {
    private List<Terminal> terminals;

    public TerminalService() {
        terminals = new ArrayList<>();
        terminals.add(new Terminal(1L, "Terminal LP"));
        terminals.add(new Terminal(2L, "Terminal TJ"));
        terminals.add(new Terminal(3L, "Terminal SC"));
    }


    public List<Terminal> findTerminalByName() {
        //throw new UnsupportedOperationException("Not supported yet");
        return terminals;
    }

    public List<Terminal> findTerminalByName(String terminalName) {
        return terminals.stream()
                .filter(terminal -> terminal.getName().equals(terminalName))
                .collect(Collectors.toList());
    }

    public Terminal createTerminal(Terminal newTerminal) {
        terminals.add(newTerminal);
        return findTerminalByName(newTerminal.getName()).get(0);
    }

    public Terminal updateTerminal(Terminal newTerminal) {
        Terminal currentTerminal = findTerminalById(newTerminal);

        deleteTerminal(currentTerminal);

        currentTerminal.setName(newTerminal.getName());

        terminals.add(currentTerminal);

        return currentTerminal;
    }

    private Terminal findTerminalById(Terminal currentTerminal) {
        Optional<Terminal> searchedTerminal = terminals.stream()
                .filter( terminal -> terminal.getId().equals(currentTerminal.getId()) )
                .findFirst();

        if (searchedTerminal.isEmpty()) throw new ElementNotFoundException("The "+currentTerminal+" does not exist in the database");

        return searchedTerminal.get();
    }

    public void deleteTerminal(Terminal currentTerminal) {
        if (!terminals.remove(currentTerminal)) throw new ElementWasNotRemoved("The"+currentTerminal+" was not removed");
    }
}

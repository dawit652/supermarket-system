package com.supermarket.application.services;

import com.supermarket.application.models.Branch;
import com.supermarket.data.dao.BranchDAO;

import java.sql.SQLException;
import java.util.List;

public class BranchService {
    private final BranchDAO branchDAO;

    public BranchService() {
        this.branchDAO = new BranchDAO();
    }

    public List<Branch> getAllBranches() throws SQLException {
        return branchDAO.getAllBranches();



    }
}

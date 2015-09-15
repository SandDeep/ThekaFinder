package com.theka.search.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.theka.search.domain.SolrData;
import com.theka.search.domain.SolrData.NearBy;

public class DbConnect {

	private JdbcTemplate jdbcTemplate;

	public List<SolrData> getData() {
		
		String sql = " SELECT t.LocId, t.Place, t.Address, t.Metadata, l.LocNearBy"
				+ "  FROM t_theka_info t, t_location l" + " WHERE t.LocId = l.Id";
		List<SolrData> thekaList = jdbcTemplate.query(sql, new RowMapper<SolrData>() {

			@Override
			public SolrData mapRow(ResultSet rs, int arg) throws SQLException {
				SolrData info = new SolrData();
				info.setLocId(rs.getInt("LocId"));
				info.setPlace(rs.getString("Place"));
				info.setAddress(rs.getString("Address"));
				info.setMetadata(rs.getString("Metadata"));
				info.setLocNearBy(rs.getString("LocNearBy"));
				return info;
			}
		});
		
		String sql2 = " select l.Id, l.LocationName, l.LocNearBy"
				+ "  from t_location l where l.Id not in (select i.LocId from t_theka_info i)";
		List<SolrData> thekaList1 = jdbcTemplate.query(sql2, new RowMapper<SolrData>() {

			@Override
			public SolrData mapRow(ResultSet rs, int arg) throws SQLException {
				SolrData info = new SolrData();
				info.setLocId(rs.getInt("Id"));
				info.setPlace(rs.getString("LocationName"));
				info.setLocNearBy(rs.getString("LocNearBy"));
				return info;
			}
		});

		thekaList.addAll(thekaList1);

		String qry = " select t.LocId, t.Place, t.Address, t.Metadata from t_theka_info t WHERE FIND_IN_SET(t.LocId, ?);";
		
		for (SolrData data : thekaList) {
			if (data.getLocNearBy() != null) {
				List<NearBy> nearList = jdbcTemplate.query(qry, new RowMapper<NearBy>() {

					@Override
					public NearBy mapRow(ResultSet rs, int arg1) throws SQLException {
						NearBy near = new NearBy();
						near.setNearLocId(rs.getInt("LocId"));
						near.setPlace(rs.getString("Place"));
						near.setAddress(rs.getString("Address"));
						near.setMetadata(rs.getString("Metadata"));
						return near;
					}
				}, new Object[] { data.getLocNearBy() });

				data.setNearByList(nearList);
			}
		}
		return thekaList;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

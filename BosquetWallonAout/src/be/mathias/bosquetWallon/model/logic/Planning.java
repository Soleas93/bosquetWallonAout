package be.mathias.bosquetWallon.model.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Planning {
	private int id = 0;
	private LocalDate beginDate;
	private LocalDate endDate;
	private List<Show> showList;
	
	public Planning(int id, LocalDate beginDate, LocalDate endDate, List<Show> showList) {
		setId(id);
		setBeginDate(beginDate);
		setEndDate(endDate);
		setShowList(showList);
	}
	
	public Planning(LocalDate beginDate, LocalDate endDate) {
		this(0, beginDate, endDate, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		//TODO : concurrence
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		//TODO : concurrence
		this.endDate = endDate;
	}

	public List<Show> getShowList() {
		return showList;
	}

	private void setShowList(@Nullable List<Show> showList) {
		if(showList != null)
			this.showList = showList;
		else
			this.showList = new ArrayList<Show>();
	}
	
	public void addShow(Show show) {
		if(show != null)
			this.showList.add(show);
	}
	
	public void addShows(@NonNull Collection<? extends Show> shows) {
		this.showList.addAll(shows);
	}
	
	
}

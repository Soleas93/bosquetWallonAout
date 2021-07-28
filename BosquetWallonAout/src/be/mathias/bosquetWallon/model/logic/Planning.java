package be.mathias.bosquetWallon.model.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Planning {
	private int id = 0;
	private LocalDate beginDate;
	private LocalDate endDate;
	private List<Show> showList = new ArrayList<Show>();
}

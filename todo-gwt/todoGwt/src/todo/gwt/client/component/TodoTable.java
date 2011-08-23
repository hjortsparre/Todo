package todo.gwt.client.component;

import java.util.List;

import todo.gwt.client.Session;
import todo.gwt.client.TodoGwt;
import todo.gwt.client.dto.AccountDTO;
import todo.gwt.client.dto.ResponseDTO;
import todo.gwt.client.dto.TodoDTO;
import todo.gwt.client.service.TodoServiceAsync;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TodoTable extends Composite {

	private FlexTable checkedOutTable = new FlexTable();
	private FlexTable checkedInTable = new FlexTable();
	private VerticalPanel wrapper = new VerticalPanel();

	private List<TodoDTO> items;

	private Label message = new Label();

	public TodoTable() {
		initWidget(wrapper);

		Label checkedOutLabel = new Label("Checked Out Todo Items");
		checkedOutLabel.getElement().getStyle().setFontSize(18, Unit.PX);
		checkedOutLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		checkedOutLabel.getElement().getStyle().setPaddingTop(12, Unit.PX);

		Label checkedInLabel = new Label("Checked In Todo Items");
		checkedInLabel.getElement().getStyle().setFontSize(18, Unit.PX);
		checkedInLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		checkedInLabel.getElement().getStyle().setPaddingTop(12, Unit.PX);

		wrapper.add(message);
		wrapper.add(checkedOutLabel);
		wrapper.add(checkedOutTable);
		wrapper.add(checkedInLabel);
		wrapper.add(checkedInTable);

	}

	public void show(List<TodoDTO> items) {
		this.items = items;
		generateTable();
	}

	private void generateTable() {

		checkedOutTable.removeAllRows();
		checkedInTable.removeAllRows();

		for (int i = 0; i < items.size(); i++) {

			final TodoDTO todo = items.get(i);

			if (todo.getCheckedOutBy() != null) {

				Label checkInLink = new Label("Check In");
				checkInLink.getElement().getStyle().setColor("BLUE");
				checkInLink.getElement().getStyle()
						.setTextDecoration(TextDecoration.UNDERLINE);
				checkInLink.getElement().getStyle().setCursor(Cursor.POINTER);

				checkInLink.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						TodoServiceAsync.instance.checkIn(
								Session.suppliedEmail,
								Session.suppliedPassword, todo.getId(),
								new AsyncCallback<ResponseDTO>() {

									@Override
									public void onSuccess(ResponseDTO result) {
										if (result.getCode() == 0) {
											message.setText("");
											todo.setCheckedOutBy(null);
											generateTable();
										} else {
											message.setText(result.getMessage());
										}
									}

									@Override
									public void onFailure(Throwable caught) {
										message.setText(caught.getMessage());
									}
								});
					}
				});

				int rowCount = checkedOutTable.getRowCount();
				checkedOutTable.setWidget(rowCount, 0, new Label(items.get(i)
						.getId() + ""));
				checkedOutTable.setWidget(rowCount, 1, new Label(" - "));
				checkedOutTable.setWidget(rowCount, 2, checkInLink);
				checkedOutTable.setWidget(rowCount, 3, new Label(" - "));

				checkedOutTable.setWidget(rowCount, 4, new Label(items.get(i)
						.getName()));
				checkedOutTable.setWidget(rowCount, 5, new Label("( "
						+ items.get(i).getCheckedOutBy().getEmail() + " )"));
			} else {

				Label checkOutLink = new Label("Check Out");
				checkOutLink.getElement().getStyle().setColor("BLUE");
				checkOutLink.getElement().getStyle()
						.setTextDecoration(TextDecoration.UNDERLINE);
				checkOutLink.getElement().getStyle().setCursor(Cursor.POINTER);
				checkOutLink.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						TodoServiceAsync.instance.checkOut(
								Session.suppliedEmail,
								Session.suppliedPassword, todo.getId(),
								new AsyncCallback<ResponseDTO>() {

									@Override
									public void onSuccess(ResponseDTO result) {
										if (result.getCode() == 0) {
											message.setText("");
											todo.setCheckedOutBy(new AccountDTO());
											todo.getCheckedOutBy().setEmail(
													Session.suppliedEmail);
											generateTable();
										} else {
											message.setText(result.getMessage());
										}
									}

									@Override
									public void onFailure(Throwable caught) {
										message.setText(caught.getMessage());
									}
								});
					}
				});

				int rowCount = checkedInTable.getRowCount();
				checkedInTable.setWidget(rowCount, 0, new Label(items.get(i)
						.getId() + ""));
				checkedInTable.setWidget(rowCount, 1, new Label(" - "));
				checkedInTable.setWidget(rowCount, 2, checkOutLink);
				checkedInTable.setWidget(rowCount, 3, new Label(" - "));
				checkedInTable.setWidget(rowCount, 4, new Label(items.get(i)
						.getName()));

			}
		}
	}
}

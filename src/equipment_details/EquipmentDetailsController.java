package equipment_details;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class EquipmentDetailsController {
    @FXML
    public Label eq_name;
    @FXML
    public ImageView eq_img;
    @FXML
    public TableView location_table;
    @FXML
    public TableColumn clm_location;
    @FXML
    public TableColumn clm_number;
}

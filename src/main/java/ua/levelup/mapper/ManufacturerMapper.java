//package ua.levelup.mapper;
//
//import ua.levelup.model.Manufacturer;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ManufacturerMapper extends AbstractMapper<Manufacturer> {
//
//    private final static String ID = "id";
//    private final static String NAME = "manufacturer_name";
//
//    @Override
//    protected Manufacturer getObject(ResultSet resultSet) throws SQLException{
//        Manufacturer manufacturer = new Manufacturer();
//        manufacturer.setId(resultSet.getInt(ID));
//        manufacturer.setName(resultSet.getString(NAME));
//        return manufacturer;
//    }
//}

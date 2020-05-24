package com.sayiamfun.file20200414;

/**
 * 拼装kudu建表语句
 */
public class CreatKuduTableSql {

    public static void main(String[] args) {
        String s = "veh_model_name,un_name,trade_mark,common_name,product_name,driving_range_work,curb_weight,total_mass,mass_long,mass_width,mass_high,wheelbase,max_speed,top_speed_30,passenger_category,dict_uses,dict_name,fuel_type,energy_storage_device,energy_storage_type,energy_total_capacity,energy_assembly_mass,displacement,power,individual_model,individual_shape,individual_shape_size,monomer_nominal,three_hour_rated_capacity,monomer_mass,monomer_num,monomer_manufacturer,assembly_manufacturer,module_model,min_module_standard,min_module_rated_capacity,energy_storage_model,energy_storage_combination,energy_assembly_standard,energy_assembly_outpower,assembly_nominal_capacity,quick_change_device,cathode_material,anode_material,electrolyte_composition,electrolyte_morphology,veh_energy_sys_model,veh_energy_manufacturer,charging_method,battery_class,pack_energy_density,controller_model,controller_manufacturer";
        String[] split = s.split(",");
        StringBuilder stringBuilder = new StringBuilder(500);
        for (String s1 : split) {
            stringBuilder.append("out.writeUTF(StringUtils.getValue(this."+s1+",\"\"));");
        }

//        System.out.println(stringBuilder.toString());
    }
}

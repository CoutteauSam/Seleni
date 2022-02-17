package scouttea.seleni.common.registry;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import scouttea.seleni.common.datatypes.LinearFunction;

//UNUSED
public class ModDataTypes {
    public static final SerializableDataType<LinearFunction> LINEAR_FUNCTION = SerializableDataType.compound(LinearFunction.class,
            new SerializableData()
                    .add("scale", SerializableDataTypes.FLOAT, 0.0f)
                    .add("constant", SerializableDataTypes.FLOAT, 1.0f),
            (dataInst) -> new LinearFunction(dataInst.getFloat("scale"), dataInst.getFloat("constant")),
            (data, inst) -> {
                SerializableData.Instance dataInst = data.new Instance();
                dataInst.set("scale", inst.getScale());
                dataInst.set("constant", inst.getScale());
                return dataInst;
            });
}

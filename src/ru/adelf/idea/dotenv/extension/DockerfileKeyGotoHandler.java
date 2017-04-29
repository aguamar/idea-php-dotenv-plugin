package ru.adelf.idea.dotenv.extension;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.plugins.docker.dockerFile.parser.psi.DockerFileEnvRegularDeclaration;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLScalar;
import org.jetbrains.yaml.psi.YAMLSequenceItem;
import ru.adelf.idea.dotenv.api.EnvironmentVariablesApi;
import ru.adelf.idea.dotenv.util.EnvironmentVariablesUtil;

public class DockerfileKeyGotoHandler implements GotoDeclarationHandler {

    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(@Nullable PsiElement psiElement, int i, Editor editor) {

        if(psiElement == null || psiElement.getParent() == null) {
            return new PsiElement[0];
        }

        if(!psiElement.getContainingFile().getName().equals("Dockerfile")) {
            return new PsiElement[0];
        }

        psiElement = psiElement.getParent();

        if(!(psiElement instanceof DockerFileEnvRegularDeclaration)) {
            return new PsiElement[0];
        }

        return EnvironmentVariablesApi.getKeyUsages(psiElement.getProject(), EnvironmentVariablesUtil.getKeyFromString((((DockerFileEnvRegularDeclaration) psiElement).getDeclaredName().getText())));
    }

    @Nullable
    @Override
    public String getActionText(DataContext dataContext) {
        return null;
    }
}